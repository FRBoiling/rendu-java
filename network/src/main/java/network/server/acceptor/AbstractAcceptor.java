package network.server.acceptor;

import lombok.extern.slf4j.Slf4j;
import network.common.NettyEvent;
import network.common.ServiceThread;

import java.net.SocketAddress;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 14:15
 */
@Slf4j
public abstract class AbstractAcceptor extends AbstractNettyAcceptor {

    protected final NettyEventExecutor nettyEventExecutor = new NettyEventExecutor();

    protected abstract IChannelEventListener getChannelEventListener();

    public AbstractAcceptor(SocketAddress localAddress) {
        super(localAddress);
    }

    public void putNettyEvent(final NettyEvent event) {
        this.nettyEventExecutor.putNettyEvent(event);
    }

    public class NettyEventExecutor extends ServiceThread {
        private final LinkedBlockingQueue<NettyEvent> eventQueue = new LinkedBlockingQueue<NettyEvent>();
        private final int MaxSize = 10000;

        public void putNettyEvent(final NettyEvent event) {
            if (this.eventQueue.size() <= MaxSize) {
                this.eventQueue.add(event);
            } else {
                log.warn("event queue size[{}] enough, so drop this event {}", this.eventQueue.size(),
                        event.toString());
            }
        }

        public void run() {
            log.info(this.getServiceName() + " service started");

            final IChannelEventListener listener = AbstractAcceptor.this.getChannelEventListener();
            while (!this.isStoped()){
                try {
                    NettyEvent event = this.eventQueue.poll(3000, TimeUnit.MILLISECONDS);
                    if (event != null && listener != null) {
                        switch (event.getType()) {
                            case IDLE:
                                listener.onChannelIdle(event.getRemoteAddr(), event.getChannel());
                                break;
                            case CLOSE:
                                listener.onChannelClose(event.getRemoteAddr(), event.getChannel());
                                break;
                            case CONNECT:
                                listener.onChannelConnect(event.getRemoteAddr(), event.getChannel());
                                break;
                            case EXCEPTION:
                                listener.onChannelException(event.getRemoteAddr(), event.getChannel());
                                break;
                            default:
                                break;
                        }
                    }
                } catch (Exception e) {
                    log.warn(this.getServiceName() + " service has exception. ", e);
                }
            }
        }

        @Override
        public String getServiceName() {
            return AbstractAcceptor.class.getSimpleName();
        }
    }
}

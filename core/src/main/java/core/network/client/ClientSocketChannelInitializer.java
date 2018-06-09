package core.network.client;

import core.network.IChannelHandlerHolder;
import core.network.INetworkConsumer;
import core.network.INetworkEventListener;
import core.network.IService;
import core.network.codec.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import lombok.Getter;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-24
 * Time: 10:03
 */
@Getter
public class ClientSocketChannelInitializer extends ChannelInitializer implements IChannelHandlerHolder {

    //实现userEventTriggered方法，并在state是WRITER_IDLE的时候发送一个心跳包到sever端，告诉server端我还活着
    private final ClientIdleStateTrigger idleStateTrigger = new ClientIdleStateTrigger();
    //封包
    private final PacketWriter writer = new PacketWriter();
    //message的编码器
    private final PacketEncoder encoder = new PacketEncoder();
    //
    private ClientMessageExecutor messageExecutor;

    // 重连watchdog
    protected final HashedWheelTimer timer = new HashedWheelTimer(new ThreadFactory() {
        private AtomicInteger threadIndex = new AtomicInteger(0);
        public Thread newThread(Runnable r) {
            return new Thread(r, "NettyClientConnectorExecutor_" + this.threadIndex.incrementAndGet());
        }
    });
    private ClientWatchdog watchdog;

    ClientSocketChannelInitializer(INetworkConsumer consumer, INetworkEventListener listener) {
        messageExecutor = new ClientMessageExecutor(consumer, listener);
        this.watchdog = new ClientWatchdog(timer){
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                        //将[ConnectorWatchdog]装载到handler链中，当链路断掉之后，会触发ConnectionWatchdog #channelInActive方法
                        this,
                        //每隔30s的时间触发一次userEventTriggered的方法，并且指定IdleState的状态位是WRITER_IDLE
                        new IdleStateHandler(0, 50, 0, TimeUnit.SECONDS),
                        idleStateTrigger,
                        writer,
                        encoder,
                        //拆包
                        new PacketReader(),
                        //message的解码器
                        new PacketDecoder(),
                        messageExecutor
                };
            }
        };
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pip = channel.pipeline();
        pip.addLast(handlers());
    }

    @Override
    public ChannelHandler[] handlers() {
        return  watchdog.handlers();
    }
}

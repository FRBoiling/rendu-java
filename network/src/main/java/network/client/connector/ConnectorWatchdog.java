package network.client.connector;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 16:11
 */

@Slf4j
@ChannelHandler.Sharable
public abstract class ConnectorWatchdog extends ChannelInboundHandlerAdapter implements TimerTask, IChannelHandlerHolder {
    private final Bootstrap bootstrap;
    private final Timer timer;
    private final int port;
    private final String host;

    private volatile boolean reconnect = true;
    private int attempts;

    public ConnectorWatchdog(Bootstrap bootstrap, Timer timer, int port, String host) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        attempts = 0;

        log.info("Connects with {}.", channel);

        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        boolean doReconnect = reconnect;
        if (doReconnect) {
            if (attempts < 12) {
                attempts++;
            }
            long timeout = 2 << attempts;
            timer.newTimeout(this, timeout, MILLISECONDS);
        }

        log.warn("Disconnects with {}, port: {},host {}, reconnect: {}.", ctx.channel(), port,host, doReconnect);

        ctx.fireChannelInactive();
    }

    public boolean isReconnect() {
        return reconnect;
    }

    public void setReconnect(boolean reconnect) {
        this.reconnect = reconnect;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        ChannelFuture future;
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host,port);
        }

        future.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture f) throws Exception {
                boolean succeed = f.isSuccess();

                log.warn("Reconnects with {}, {}.", host+":"+port, succeed ? "succeed" : "failed");

                if (!succeed) {
                    f.channel().pipeline().fireChannelInactive();
                }
            }
        });
    }
}

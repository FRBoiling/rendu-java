package core.network.client;

import core.network.IChannelHandlerHolder;
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
public abstract class ClientWatchdog extends ChannelInboundHandlerAdapter implements TimerTask, IChannelHandlerHolder {
    private int port;
    private String host;

    private Bootstrap bootstrap;
    private Timer timer;

    private volatile boolean reconnect = true;
    private int attempts;

    public ClientWatchdog(Timer timer) {
        this.timer = timer;
    }

    public void init(Bootstrap bootstrap, String host, int port) {
        this.bootstrap = bootstrap;
        this.port = port;
        this.host = host;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        attempts = 0;
        log.info("Connect with {}.", channel);
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (reconnect) {
            if (attempts < 12) { //尝试连接12次
                attempts++;
                long timeout = 2 << attempts;
                timer.newTimeout(this, timeout, MILLISECONDS);
                log.warn("Disconnect on {}, host {}, port: {}, reconnect: {} attempts:{}.", ctx.channel(), port, host, reconnect, attempts);
            }
        }
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
            future = bootstrap.connect(host, port);
        }

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
                boolean succeed = f.isSuccess();
                log.warn("Reconnect with {}:{}, {}.", host, port, succeed ? "succeed" : "failed");
                if (!succeed) {
                    f.channel().pipeline().fireChannelInactive();
                }else{
                    System.out.println("重连成功");
                }
            }
        });
    }
}

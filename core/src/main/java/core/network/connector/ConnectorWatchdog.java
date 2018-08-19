package core.network.connector;

import core.base.serviceframe.IService;
import core.network.IChannelHandlerHolder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
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
    ConnectorNetworkService netWorkService;
    private Timer timer;

    private volatile boolean reconnect = true;
//    private int attempts;

    private long timeoutDelay = 1;

    ConnectorWatchdog(Timer timer) {
        this.timer = timer;
    }

    public void init(ConnectorNetworkService service, String host, int port) {
        this.netWorkService = service;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
//        attempts = 0;
        log.debug("Connect with {}.", channel);
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (isReconnect()) {
//            if (attempts < 12) { //尝试连接12次
//                attempts++;
//                long timeout = 2 << attempts;
//                timer.newTimeout(this, timeout, MILLISECONDS);
//                log.warn("Disconnect on {}, host {}, listenPort: {}, reconnect: {} attempts:{}.", ctx.channel(), port, host, reconnect, attempts);
//            }
            //timeoutDelay 秒后重连
            timer.newTimeout(this, timeoutDelay, MILLISECONDS);
            log.warn("Disconnect on {},try to reconnect.", ctx.channel());
        }
        ctx.fireChannelInactive();
    }

    private boolean isReconnect() {
        return reconnect;
    }

    void setReconnect(boolean reconnect) {
        this.reconnect = reconnect;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
       netWorkService.start();
    }
}

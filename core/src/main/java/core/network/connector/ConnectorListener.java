package core.network.connector;

import core.network.ServiceState;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 15:40
 */
@Slf4j
public class ConnectorListener implements ChannelFutureListener {
    private ConnectorNetworkService service;
    /**
     * 重连间隔，单位：秒
     */
    private final long ReconnectDelay = 1;

    ConnectorListener(ConnectorNetworkService service) {
        this.service = service;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            service.setState(ServiceState.RUNNING);
            log.debug("Connect to ip:{} listenPort:{} successfully", service.getBuilder().getIp(), service.getBuilder().getPort());
        } else {
//          future.cause().printStackTrace();
            log.info("Failed to connect to ip:{} listenPort:{}, try connect after {}s ", service.getBuilder().getIp(), service.getBuilder().getPort(), ReconnectDelay);
            future.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    service.start();
                }
            }, ReconnectDelay, TimeUnit.SECONDS);
        }
    }
}

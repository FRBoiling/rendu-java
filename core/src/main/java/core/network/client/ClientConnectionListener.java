package core.network.client;

import core.network.ServiceState;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
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
public class ClientConnectionListener implements ChannelFutureListener {
    private ClientNetworkService service;

    public ClientConnectionListener(ClientNetworkService service) {
        this.service = service;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            service.setState(ServiceState.RUNNING);
            log.info("Connect to ip:{} listenPort:{} successfully", service.getBuilder().getIp(), service.getBuilder().getPort());
        } else {
//          future.cause().printStackTrace();
            log.info("Failed to connect to ip:{} listenPort:{}, try connect after 5s ", service.getBuilder().getIp(), service.getBuilder().getPort());
            future.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    service.start();
                }
            }, 5, TimeUnit.SECONDS);
        }
    }
}

package core.network.server.forClientApp;

import core.network.ServiceState;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 16:49
 */
@Slf4j
public class BindListener implements ChannelFutureListener {
    private NetworkService service;

    BindListener(NetworkService service) {
        this.service = service;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            service.setState(ServiceState.RUNNING);
            log.info("Server on listenPort:{} is start", service.getBuilder().getPort());
        } else {
            log.error("Failed to bind on listenPort:{}", service.getBuilder().getPort());
            future.cause().printStackTrace();
        }
    }
}

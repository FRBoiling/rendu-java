package core.network.client;

import core.base.serviceframe.IService;
import core.network.IResponseHandlerManager;
import core.network.*;
import core.network.INetworkEventListener;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-24
 * Time: 10:03
 */
@Data
public class ClientNetworkServiceBuilder implements INetworkServiceBuilder,ISocketClient {
    /**
     * 工作线程池线程数量
     */
    private int workerLoopGroupCount;
    /**
     * 网络消费者
     */
    private INetworkConsumer consumer;

    /**
     * 事件监听器
     */
    private INetworkEventListener listener;

    /**
     * 连接端口
     */
    private int port;

    /**
     * 连接IP
     */
    private String ip;

    @Override
    public IService createService() {
        return new ClientNetworkService(this);
    }

    @Override
    public void connect(String host,int port) {
        this.port = port;
        this.ip = host;
    }

    @Override
    public void shutdownGracefully() {

    }
}

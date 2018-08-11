package core.network.connector;

import core.base.serviceframe.IService;
import core.network.*;
import core.network.INetworkEventListener;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-24
 * Time: 10:03
 */
@Getter
@Setter
public class ConnectorNetworkServiceBuilder implements INetworkServiceBuilder{

    private String name;
    /**
     * 工作线程池线程数量
     */
    private int workerLoopGroupCount;

    /**
     * 网络消费者
     */
    private INetworkConsumer consumer;

    @Override
    public INetworkConsumer getConsumer() {
        return consumer;
    }

    /**
     * 事件监听器
     */
    private INetworkEventListener listener;

    @Override
    public INetworkEventListener getListener() {
        return listener;
    }
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
        return new ConnectorNetworkService(this);
    }
}

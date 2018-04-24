package core.network.client;

import core.network.*;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-24
 * Time: 10:03
 */
@Data
public class ClientNetworkServiceBuilder implements INetworkServiceBuilder {
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
     * 消息池
     */
    private IMessageAndHandler messageAndHandler;

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
}

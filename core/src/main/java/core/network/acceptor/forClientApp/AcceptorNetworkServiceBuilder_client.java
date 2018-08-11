package core.network.acceptor.forClientApp;

import core.base.serviceframe.IService;
import core.network.INetworkConsumer;
import core.network.INetworkEventListener;
import core.network.INetworkServiceBuilder;
import core.network.IResponseHandlerManager;
import lombok.Data;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 * <p>
 * Project: ServerCluster-Java
 * Package: core.network
 * Description: ${todo}
 * author: Boiling
 * date: 2018/4/22 0022 13:05
 * version: V1.0
 */
@Data
public class AcceptorNetworkServiceBuilder_client implements INetworkServiceBuilder {

    private String name;
    /**
     * 网络线程池线程数量
     */
    private int acceptorGroupCount;
    /**
     * 工作线程池线程数量
     */
    private int IOGroupCount;
    /**
     * 监听端口
     */
    private int port;
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
     * 消息池
     */
    private IResponseHandlerManager responseHandlerManager;

    @Override
    public IService createService() {
        return new AcceptorNetworkService_client(this);
    }
}

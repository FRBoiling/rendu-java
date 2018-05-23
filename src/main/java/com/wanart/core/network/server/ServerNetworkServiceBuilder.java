package com.wanart.core.network.server;


import com.wanart.core.network.*;
import lombok.Data;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 13:05
 * @version: V1.0
 */
@Data
public class ServerNetworkServiceBuilder implements INetworkServiceBuilder {
    /**
     * 网络线程池线程数量
     */
    private int bossLoopGroupCount;
    /**
     * 工作线程池线程数量
     */
    private int workerLoopGroupCount;

    /**
     * 监听端口
     */
    private int port;

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

    @Override
    public IService createService() {
        return new ServerNetworkService(this);
    }
}

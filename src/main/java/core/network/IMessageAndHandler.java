package core.network;

import core.network.codec.Packet;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 13:11
 * @version: V1.0
 */
public interface IMessageAndHandler {
//    /**
//     * 获取消息
//     *
//     * @param messageId
//     * @return
//     */
//    AbstractMessage getMessage(int messageId);
//
//    /**
//     * 获取消息id
//     * @param message
//     * @return
//     */
//    int getMessageId(AbstractMessage message);

    /**
     * 获取handler
     * @param handlerName
     * @return
     */
    AbstractHandler getHandler(String handlerName);

    /**
     * 注册
     * @param messageId
     * @param handler
     */
    void register(int messageId, Class<? extends AbstractHandler> handler);

}

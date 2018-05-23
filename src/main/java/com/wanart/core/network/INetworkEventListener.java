package com.wanart.core.network;

import io.netty.channel.ChannelHandlerContext;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 13:10
 * @version: V1.0
 */
public interface INetworkEventListener {
     /**
     * 连接建立
     *
     * @param ctx
     */
    void onConnected(ChannelHandlerContext ctx);

    /**
     * 连接断开
     * * @param ctx
     */
    void onDisconnected(ChannelHandlerContext ctx);

    /**
     * 异常发生
     * * @param ctx
     * * @param cause
     */
    void onExceptionOccur(ChannelHandlerContext ctx, Throwable cause);
}

package core.network;

import core.network.codec.Packet;
import io.netty.channel.Channel;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 13:09
 * @version: V1.0
 */
public interface INetworkConsumer {
    /**
     * 执行具体的指令
     *
     * @param packet packet
     * @param channel channel
     */
    void consume(Packet packet, Channel channel);
}

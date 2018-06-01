package core.network.server;

import core.network.IService;
import core.network.codec.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network.server
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 15:28
 * @version: V1.0
 */
public class ServerSocketChannelInitializer extends ChannelInitializer {
    private IService service;

    ServerSocketChannelInitializer(IService service) {
        this.service = service;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pip = channel.pipeline();
        int maxLength = 1048576;
        int lengthFieldLength = 4;
        int ignoreLength = -4;
        int offset = 0;
        pip.addLast(new PacketReader());
        pip.addLast(new PacketDecoder());
        pip.addLast(new PacketWriter());
        pip.addLast(new PacketEncoder());
        pip.addLast(new ServerMessageExecutor(service));
//        for (ChannelHandler handler : builder.getExtraHandlers()) {
//            pip.addLast(handler);
//        }
    }
}

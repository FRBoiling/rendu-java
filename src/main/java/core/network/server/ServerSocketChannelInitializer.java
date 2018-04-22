package core.network.server;

import core.network.INetworkServiceBuilder;
import core.network.MessageExecutor;
import core.network.codec.PacketDecoder;
import core.network.codec.PacketEncoder;
import core.network.codec.PacketReader;
import core.network.codec.PacketWriter;
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
    private ServerNetworkServiceBuilder builder;

    ServerSocketChannelInitializer(INetworkServiceBuilder builder) {
        this.builder =(ServerNetworkServiceBuilder) builder;

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
        pip.addLast(new MessageExecutor(builder.getConsumer(), builder.getListener()));
//        for (ChannelHandler handler : builder.getExtraHandlers()) {
//            pip.addLast(handler);
//        }
    }
}

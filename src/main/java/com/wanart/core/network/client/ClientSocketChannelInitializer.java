package com.wanart.core.network.client;

import com.wanart.core.network.INetworkServiceBuilder;
import com.wanart.core.network.MessageExecutor;
import com.wanart.core.network.codec.PacketDecoder;
import com.wanart.core.network.codec.PacketEncoder;
import com.wanart.core.network.codec.PacketReader;
import com.wanart.core.network.codec.PacketWriter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-24
 * Time: 10:03
 */
public class ClientSocketChannelInitializer extends ChannelInitializer {
    private ClientNetworkServiceBuilder builder;
    ClientSocketChannelInitializer(INetworkServiceBuilder builder) {
        this.builder =(ClientNetworkServiceBuilder) builder;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pip = channel.pipeline();
        pip.addLast(new PacketReader());
        pip.addLast(new PacketDecoder());
        pip.addLast(new PacketWriter());
        pip.addLast(new PacketEncoder());
        pip.addLast(new MessageExecutor(builder.getConsumer(), builder.getListener()));
    }
}

package core.network.client;

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

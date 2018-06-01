package core.network.client;

import core.network.IService;
import core.network.codec.*;
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
    private IService service;
    ClientSocketChannelInitializer(IService service) {
        this.service = service;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pip = channel.pipeline();
        pip.addLast(new PacketReader());
        pip.addLast(new PacketDecoder());
        pip.addLast(new PacketWriter());
        pip.addLast(new PacketEncoder());
        pip.addLast(new ClientMessageExecutor(service));
    }
}

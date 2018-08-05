package core.network.client;


import core.network.INetworkConsumer;
import core.network.INetworkEventListener;
import core.network.codec.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-01
 * Time: 11:21
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientMessageExecutor extends SimpleChannelInboundHandler<Packet> {
    private INetworkConsumer consumer;
    private INetworkEventListener listener;

    ClientMessageExecutor(INetworkConsumer consumer,INetworkEventListener listener) {
        this.consumer = consumer;
        this.listener = listener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        consumer.consume(msg, ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        listener.onExceptionOccur(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.listener.onConnected(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.listener.onDisconnected(ctx);
    }
}

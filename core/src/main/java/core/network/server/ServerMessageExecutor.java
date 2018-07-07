package core.network.server;

import core.network.INetworkConsumer;
import core.network.INetworkEventListener;
import core.base.serviceframe.IService;
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
 * Time: 11:37
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerMessageExecutor extends SimpleChannelInboundHandler<Packet> {
    private INetworkConsumer consumer;
    private INetworkEventListener listener;
    private ServerNetworkService service;

    public ServerMessageExecutor(IService service) {
        this.service = (ServerNetworkService)service;
        this.consumer = this.service.getBuilder().getConsumer();
        this.listener = this.service.getBuilder().getListener();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        log.info("channelRead0 recv");
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

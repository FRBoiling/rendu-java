package core.network.client;


import core.network.INetworkConsumer;
import core.network.INetworkEventListener;
import core.network.IService;
import core.network.codec.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-01
 * Time: 11:21
 */
@Slf4j
public class ClientMessageExecutor extends SimpleChannelInboundHandler<Packet> {
    private INetworkConsumer consumer;
    private INetworkEventListener listener;
    private ClientNetworkService service;

    public ClientMessageExecutor(IService service) {
        this.service = (ClientNetworkService)service;
        this.consumer = this.service.getBuilder().getConsumer();
        this.listener = this.service.getBuilder().getListener();
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
        ctx.channel().closeFuture().sync();
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                log.info("channelInactive reconnect2");
                service.start();
            }
        }, 1L, TimeUnit.SECONDS);
    }
}

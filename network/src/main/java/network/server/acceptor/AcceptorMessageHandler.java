package network.server.acceptor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import network.codec.Packet;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 14:45
 */
@Slf4j
@ChannelHandler.Sharable
public class AcceptorMessageHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,  Packet message) throws Exception {
        Channel channel = ctx.channel();
        log.info("AcceptorMessageHandler-->>>"+message.toString());

//        // 接收到发布信息的时候，要给Client端回复ACK
//        channel.writeAndFlush(new Acknowledge(message.sequence())).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}
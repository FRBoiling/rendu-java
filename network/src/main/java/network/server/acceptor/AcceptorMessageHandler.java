package network.server.acceptor;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 14:45
 */
@Slf4j
@ChannelHandler.Sharable
class AcceptorMessageHandler extends SimpleChannelInboundHandler<Package> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Package message) throws Exception {
        Channel channel = ctx.channel();
        log.info(message.toString());

//        // 接收到发布信息的时候，要给Client端回复ACK
//        channel.writeAndFlush(new Acknowledge(message.sequence())).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}
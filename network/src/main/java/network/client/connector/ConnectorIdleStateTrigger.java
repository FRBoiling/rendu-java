package network.client.connector;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.global.G2GM;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 16:29
 */
@Slf4j
@ChannelHandler.Sharable
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {
//    private static final Logger logger = LoggerFactory.getLogger(ConnectorIdleStateTrigger.class);
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                log.info("need send heartbeats");
                //发送心跳包
                G2GM.MSG_G2GM_HEARTBEAT.Builder builder = G2GM.MSG_G2GM_HEARTBEAT.newBuilder();
                ctx.channel().writeAndFlush(builder.build());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
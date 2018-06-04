package network.client.connector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 16:29
 */
@Slf4j
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {
//    private static final Logger logger = LoggerFactory.getLogger(ConnectorIdleStateTrigger.class);
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                log.info("need send heartbeats");
                //TODO:boil 发送心跳包
//                ctx.writeAndFlush(Heartbeats.heartbeatContent());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
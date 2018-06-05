package network.client.connector;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import network.common.Acknowledge;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 16:37
 */
@Slf4j
@ChannelHandler.Sharable
public class ConnectorMessageHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到server端的Ack信息，无需再次发送信息");
        if(msg instanceof Acknowledge){
            log.info("收到server端的Ack信息，无需再次发送信息");
//            messagesNonAcks.remove(((Acknowledge)msg).sequence());
        }
    }

}
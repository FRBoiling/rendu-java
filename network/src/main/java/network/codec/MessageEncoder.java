package network.codec;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 13:57
 */

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;
import protocol.msgId.Id;

import java.util.List;
@Slf4j
@ChannelHandler.Sharable
public class MessageEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
        log.info("MessageEncoder");
        Packet packet = new Packet();
        int msgId = Id.getInst().getMessageId(msg.getClass());
        packet.setMsgId(msgId);
        if (msg instanceof MessageLite) {
            packet.msg = ((MessageLite) msg).toByteArray();
            out.add(packet);
            return;
        }
        if (msg instanceof MessageLite.Builder) {
            packet.msg = ((MessageLite.Builder) msg).build().toByteArray();
            out.add(packet);
        }
    }
}

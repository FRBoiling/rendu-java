package core.network.codec;

import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import protocol.msgId.Id;

import java.util.List;

/**
 * 数据包编码器
 *
 * @author boiling
 */
@ChannelHandler.Sharable
//public class PacketEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {
public class PacketEncoder extends MessageToMessageEncoder<MessageLite> {
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageLite msg, List<Object> out) throws Exception {
        int msgId = Id.getInst().getMessageId(msg.getClass());
        Packet packet = new Packet();
        packet.setMsgId(msgId);
//        if (msg instanceof MessageLite) {
//            packet.msg = ((MessageLite) msg.toByteArray();
//            packet.setMsgLength((short) packet.msg.length);
//            out.add(packet);
//            return;
//        }
//        if (msg instanceof MessageLite.Builder) {
//            packet.msg = ((MessageLite.Builder) msg).build().toByteArray();
//            packet.setMsgLength((short) packet.msg.length);
//            out.add(packet);
//        }
        packet.setMsg(msg.toByteArray());
        packet.setMsgLength(packet.getMsgLength());
        out.add(packet);
    }
}
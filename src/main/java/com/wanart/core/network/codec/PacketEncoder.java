package com.wanart.core.network.codec;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

/**
 * 数据包编码器
 * @author boiling
 */
public class PacketEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageLiteOrBuilder msg, List out) throws Exception {
        Packet packet = new Packet();
        packet.setMsgId(protocol.msgId.Id.getInst().getMessageId(msg.getClass()));
        if (msg instanceof MessageLite) {
            packet.msg = ((MessageLite) msg).toByteArray();
            return;
        }
        if (msg instanceof MessageLite.Builder) {
            packet.msg = ((MessageLite.Builder) msg).build().toByteArray();
        }
        out.add(packet);
    }
}

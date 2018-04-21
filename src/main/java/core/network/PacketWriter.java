package core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * 数据包发送器
 * @author boiling
 */
public class PacketWriter  extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf out) throws Exception {
        int msgLength =packet.getMsgLength();
        int msgId = packet.getMsgId();
        ByteBuf msg = wrappedBuffer(packet.msg);

        out.ensureWritable(Short.BYTES +Integer.BYTES+ msgLength);
        out.writeShortLE(msgLength);
        out.writeIntLE(msgId);
        out.writeBytes(msg,msg.readerIndex(),msgLength);
    }
}

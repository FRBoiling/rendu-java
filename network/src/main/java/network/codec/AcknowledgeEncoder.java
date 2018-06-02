package network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import network.common.Acknowledge;

import static io.netty.handler.codec.http2.Http2Flags.ACK;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-02
 * Time: 16:26
 */

public class AcknowledgeEncoder extends MessageToByteEncoder<Acknowledge> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Acknowledge ack, ByteBuf out) throws Exception {
//        byte[] bytes = serializerImpl().writeObject(ack);
//        out.writeShort(MAGIC)
//                .writeByte(ACK)
//                .writeByte(0)
//                .writeLong(ack.sequence())
//                .writeInt(bytes.length)
//                .writeBytes(bytes);
    }
}

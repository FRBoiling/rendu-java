package network.codec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 14:24
 */
@Slf4j
public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//       log.info("MessageDecoder");
        Packet packet = new Packet();
        final int length = in.readableBytes();
        final int msg_id = in.readInt();
        final byte [] msg = ByteBuf2Byte(in.readBytes(length - in.readerIndex()));

        packet.setMsgId(msg_id);
        packet.setMsgLength((short) msg.length);
        packet.setMsg(msg);
        out.add(packet);
    }

    public byte[] ByteBuf2Byte(ByteBuf buff) {
        final byte[] array;
        final int offset;
        final int length = buff.readableBytes();
        if (buff.hasArray()) {
            offset = buff.arrayOffset() + buff.readerIndex();
        } else {
            offset = 0;
        }
        array = new byte[length];
        buff.getBytes(buff.readerIndex(), array, offset, length);
        return  array;
    }
}

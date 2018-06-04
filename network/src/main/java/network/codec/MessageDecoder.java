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
        log.info("MessageDecoder");
        int length = in.readShortLE();
        int msg_id = in.readIntLE();

        ByteBuf msg = in.readBytes(length - in.readerIndex());

        Packet packet = new Packet();
        packet.setMsgId(msg_id);
        packet.setMsg(msg);
        out.add(packet);
    }
}

package core.network.handler;

import core.network.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 数据包解码器
 * @author boiling
 */
public class PacketDecoder extends ByteToMessageDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacketDecoder.class);
    private static int count = 0;

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readableBytes();
        int msg_id = in.readIntLE();

        ByteBuf msg = in.readBytes(length - in.readerIndex());

        Packet packet = new Packet();
        packet.setMsgId(msg_id);
        packet.setMsg(msg);
        out.add(packet);
        LOGGER.debug("received message total count {0}", ++count);
    }
}

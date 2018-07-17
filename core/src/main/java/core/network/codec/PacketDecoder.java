package core.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 数据包解码器
 * @author boiling
 */
@Slf4j
public class PacketDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readableBytes();
        int player_id = in.readIntLE();
        int msg_id = in.readIntLE();

        int msg_length = length - in.readerIndex();
        Packet packet = new Packet((short) msg_length);

        packet.setMsgId(msg_id).setPlayerId(player_id);
        in.readBytes(packet.getMsg(),0,msg_length);
        out.add(packet);
    }
}

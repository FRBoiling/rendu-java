package network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * 数据包发送器
 *
 * @author boiling
 */

/**
 * **************************************************************************************************
 * Protocol
 * ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
 * │     4       │      2      │
 * ├ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┤
 * │             │             │
 * │  Invoke Id     Body Length             Body Content               │
 * │             │             │
 * └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 * <p>
 * 消息头6 个字节定长
 * = 4 // 消息 id int 类型
 * + 2 // 消息体body长度, short类型
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageWriter extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
//        log.info("MessageWriter");
        int msgId = packet.getMsgId();
        int msgLength = packet.getMsgLength();
        ByteBuf msg = wrappedBuffer(packet.getMsg());

        out.ensureWritable(Short.BYTES + Integer.BYTES + msgLength);
        out.writeShort(msgLength);
        out.writeInt(msgId);
        out.writeBytes(msg, msg.readerIndex(), msgLength);
    }
}

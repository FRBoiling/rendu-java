package core.network.codec.forClientApp;

import core.network.codec.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * **************************************************************************************************
 * Protocol
 * ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
 * │      2      │      4      │
 * ├ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┤
 * │             │             │
 * │ Body Length     Invoke Id              Body Content               │
 * │             │             │
 * └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 * <p>
 * 消息头6 个字节定长
 * = 4 // 消息 id int 类型
 * + 2 // 消息体body长度, short类型
 */

/**
 * 数据包发送器
 * @author boiling
 */
@ChannelHandler.Sharable
@Slf4j
public class WriterHandler extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf out) throws Exception {
        int msgLength =packet.getMsgLength();
        int msgId = packet.getMsgId();
        ByteBuf msg = wrappedBuffer(packet.getMsg());

        out.ensureWritable(Short.BYTES +Integer.BYTES+ msgLength);
        out.writeShortLE(msgLength);
        out.writeIntLE(msgId);
        out.writeBytes(msg,msg.readerIndex(),msgLength);
//        log.info("packet writer msgId {} msgLength {}",msgId,msgLength);
    }
}

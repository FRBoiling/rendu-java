package core.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

/**
 * **************************************************************************************************
 * Protocol
 * ┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐
 * │     2       │      4      │     4      │
 * ├ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┤
 * │             │             │            │
 * │ Body Length     role Id      Invoke Id      Body Content        │
 * │             │             │            │
 * └ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 * <p>
 * 消息头10个字节定长
 *  = 4 // role id int 类型
 * = 4 // 消息 id int 类型
 * + 2 // 消息体body长度, short类型
 */
/**
 * 数据包接收器
 * @author boiling
 */
public class PacketReader  extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out)throws Exception {
        in.markReaderIndex();
        int preIndex = in.readerIndex();
        int length = ReadPacketLength(in);
        if (preIndex == in.readerIndex()) {
            return;
        }
        if (length < 0) {
            throw new CorruptedFrameException("negative length: " + length);
        }

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
        } else {
            out.add(in.readRetainedSlice(length));
        }
    }

    private int ReadPacketLength(ByteBuf buffer) {
        if (buffer.readableBytes() < Short.BYTES) {
            return 0;
        }
        buffer.markReaderIndex();

        short tmp = buffer.readShort();
        if (tmp >= 0) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return 0;
            }
            return tmp + Integer.BYTES+ Integer.BYTES;//数据（protobuf）和 消息ID（int） 的长度和 一个Uid（int）
        } else {
            buffer.resetReaderIndex();
            return 0;
        }


    }
}

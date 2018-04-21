package core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

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
        if (!buffer.isReadable()) {
            return 0;
        }

        buffer.markReaderIndex();

        short tmp = buffer.readShort();
        if (tmp >= 0) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return 0;
            }
            return tmp + Integer.BYTES;
        } else {
            buffer.resetReaderIndex();
            return 0;
        }


    }
}

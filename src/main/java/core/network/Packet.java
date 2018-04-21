package core.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain=true)
public class Packet {
     int msgId;
     short msgLength;
     byte[] msg;

    public void setMsg(ByteBuf buff) {
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
        this.msg = array;
        msgLength = (short) msg.length;
    }
}

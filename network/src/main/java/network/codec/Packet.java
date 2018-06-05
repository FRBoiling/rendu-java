package network.codec;

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
    public byte[] msg;


}

package core.network.codec;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Accessors(chain=true)
public class Packet {
    private int msgId;
    private short msgLength;
    private byte[] msg;
    private int roleId =0;

    public Packet(){

    }

    public Packet(short msgLength){
        this.msgLength = msgLength;
        this.msg = new byte[msgLength];
    }

    public void setMsg(byte[] msg) {
        if (msg == null){
            log.error("setMsg fail: msg==null");
            return;
        }
        this.msgLength = (short) msg.length;
        this.msg = msg;
    }

//    public void setMsg(ByteBuf buff) {
//        final byte[] array;
//        final int offset;
//        final int length = buff.readableBytes();
//        if (buff.hasArray()) {
//            offset = buff.arrayOffset() + buff.readerIndex();
//        } else {
//            offset = 0;
//        }
//        array = new byte[length];
//        buff.getBytes(buff.readerIndex(), array, offset, length);
//        this.msg = array;
//        msgLength = (short) msg.length;
//    }
}

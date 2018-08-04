package gate.manager.response;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import protocol.manager.gate.M2G;

public class ResponseMaxCharUid implements IResponseHandler {
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        M2G.MSG_M2G_MAX_UID msg=M2G.MSG_M2G_MAX_UID.parseFrom(packet.getMsg());
        String account=msg.getAccountName();
        String channel=msg.getChannelName();
        int result =msg.getResult();
        int maxUid=msg.getMaxUid();

    }
}

package gate.client.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gate.GateService;
import gate.client.ClientSession;
import lombok.extern.slf4j.Slf4j;
import protocol.client.c2g.C2G;
import protocol.client.g2c.G2C;

@Slf4j
public class ResponseReconnectLogin implements IResponseHandler {
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ClientSession clientSession = (ClientSession) session;

        //TODO: BOIL 断线重连逻辑
        C2G.MSG_CG_RECONNECT_LOGIN msg = C2G.MSG_CG_RECONNECT_LOGIN.parseFrom(packet.getMsg());
        String accountName = msg.getAccountName();
        String token = msg.getToken();

        G2C.MSG_GC_RECONNECT_LOGIN.Builder response = G2C.MSG_GC_RECONNECT_LOGIN.newBuilder();

        if (!GateService.context.isOpened()){
            response.setResult(ErrorCode.ServerNotOpen.ordinal());
            clientSession.sendMessage(response.build());
            return;
        }


    }
}

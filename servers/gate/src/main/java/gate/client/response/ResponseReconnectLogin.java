package gate.client.response;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gate.client.ClientSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseReconnectLogin implements IResponseHandler {
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ClientSession clientSession = (ClientSession) session;
        session.closeConnection();
        return;
//        //TODO: BOIL 断线重连逻辑
//        Client.MSG_CG_RECONNECT_LOGIN msg = Client.MSG_CG_RECONNECT_LOGIN.parseFrom(packet.getMsg());
//        String accountName = msg.getUsername();
//        String token = msg.getToken();
//
//        Client.MSG_GC_RECONNECT_LOGIN.Builder response = Client.MSG_GC_RECONNECT_LOGIN.newBuilder();
//
//        if (!GateService.context.isOpened()){
//            response.setResult(ErrorCode.ServerNotOpen.ordinal());
//            clientSession.sendMessage(response.build());
//            return;
//        }


    }
}

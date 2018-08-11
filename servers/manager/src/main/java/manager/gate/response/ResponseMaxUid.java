package manager.gate.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import lombok.extern.slf4j.Slf4j;
import manager.ManagerService;
import manager.gate.GateServerSession;
import protocol.gate.manager.G2M.*;
import protocol.manager.gate.M2G;

/**
 * WANART COMPANY
 * CreatedTime : 2018/8/3 13:16
 * CTEATED BY : JIANGYUNHUI
 */
@Slf4j
public class ResponseMaxUid implements IResponseHandler {
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        
        GateServerSession gateSession=(GateServerSession)session;
        MSG_G2M_MAX_UID msg=MSG_G2M_MAX_UID.parseFrom(packet.getMsg());
        int maxUid= ++ ManagerService.context.maxRoleUid;
        M2G.MSG_M2G_MAX_UID.Builder response=M2G.MSG_M2G_MAX_UID.newBuilder();
        response.setUsername(msg.getUsername());
        response.setChannelName(msg.getChannelName());
        response.setMaxUid(maxUid);
        response.setResult(ErrorCode.SUCCESS.getValue());
        gateSession.sendMessage(response.build());
    }
}

package gate.client.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gate.Context;
import gate.GateService;
import gate.client.ClientSession;
import gate.manager.ManagerServerSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.client.Client.*;
import protocol.gate.manager.G2M;

@Slf4j
public class ResponseCreateRole implements IResponseHandler {

    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ClientSession clientSession = (ClientSession) session;
        MSG_CG_CREATE_ROLE msg = MSG_CG_CREATE_ROLE.parseFrom(packet.getMsg());
        if (clientSession.reqCreateMsg != null) {
            log.warn("account {} request create char {} sex {} head {} fail:repeat to create",
                    clientSession.getTag().toString(), msg.getName(), msg.getSex());
        }

        log.info("account {} request create char {} sex {} head {} ",
                clientSession.toString(), msg.getName(), msg.getSex());

        //获取最大uid
        G2M.MSG_G2M_MAX_UID.Builder request = G2M.MSG_G2M_MAX_UID.newBuilder();
        request.setUsername(clientSession.getAccountPOJO().getUsername());
        request.setChannelName(clientSession.getAccountPOJO().getChannelName());

        ServerTag tag = new ServerTag().setType(ServerType.Manager).setGroupId(Context.tag.getGroupId());
        AbstractSession managerSession = ManagerServerSessionMng.getInstance().getRegisterSession(tag);
        if (managerSession ==null){
            MSG_GC_CREATE_ROLE.Builder response = MSG_GC_CREATE_ROLE.newBuilder();
            response.setResult(ErrorCode.ServerNotOpen.getValue());
            clientSession.sendMessage(response.build());
            return;
        }
        managerSession.sendMessage(request.build());
        clientSession.reqCreateMsg=msg;
    }

}

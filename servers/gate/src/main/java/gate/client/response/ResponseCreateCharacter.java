package gate.client.response;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gate.GateService;
import gate.client.ClientSession;
import gate.manager.ManagerServerSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.client.c2g.C2G.*;
import protocol.gate.manager.G2M;

@Slf4j
public class ResponseCreateCharacter implements IResponseHandler {

    private String name = "";

    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ClientSession clientSession = (ClientSession) session;
        MSG_CG_CREATE_CHARACTER msg = MSG_CG_CREATE_CHARACTER.parseFrom(packet.getMsg());
        name = msg.getName().trim();
        if (clientSession.reqCreateMsg != null) {
            log.warn("account {} request create char {} sex {} head {} systemFashion {} fail:repeat to create",
                    clientSession.getTag().toString(), msg.getName(), msg.getSex(), msg.getSystemFashionId());
        }

        log.info("account {} request create char {} sex {} head {} systemFashion {}",
                clientSession.toString(), msg.getName(), msg.getSex(), msg.getSystemFashionId());

        //获取最大uid
        G2M.MSG_G2M_MAX_UID.Builder request = G2M.MSG_G2M_MAX_UID.newBuilder();
        request.setAccountName(clientSession.getAccountObject().getAccountName());
        request.setChannelName(clientSession.getAccountObject().getChannelName());

        //拿到看门狗
        ServerTag tag = new ServerTag();
        tag.setTag(ServerType.Manager, GateService.context.getManagerWatchDogGroupId(), GateService.context.getManagerWatchDogSubId());
        AbstractSession managerSession = ManagerServerSessionMng.getInstance().getRegisterSession(tag);
        managerSession.sendMessage(request.build());
    }

}

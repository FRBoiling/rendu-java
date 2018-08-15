package gate.client.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gamedb.dao.role.GetRoleUIdByNameDBOperator;
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
        String name = msg.getName();
        Integer areaId = msg.getAreaId();

        if (!areaId.equals(Context.tag.getAreaId())){
            log.error("{} create role got an wrong areaId {}", clientSession.getTag().toString(),areaId);
            return;
        }
        if (!clientSession.isRegistered()) {
            log.error("illegality session {}", clientSession.getTag().toString());
            return;
        }
        if (clientSession.reqCreateMsg != null) {
            log.warn("account {} request create role {} fail:repeat to create",
                    clientSession.getTag().toString(), name);
        }

        //TODO:BOIL 判断名字合法性
        //TODO:BOIL 1 检查屏蔽字
        //2 判断是否重名  TODO：BOIL 我觉得可以在redis上做这个。这里是先查后插。也可以直接插入
        GetRoleUIdByNameDBOperator operator = new GetRoleUIdByNameDBOperator(name);
        GateService.context.db.Call(operator, ret -> {
            GetRoleUIdByNameDBOperator op = (GetRoleUIdByNameDBOperator) ret;

            MSG_GC_CREATE_ROLE.Builder response = MSG_GC_CREATE_ROLE.newBuilder();
            ROLE_INFO.Builder role_info = ROLE_INFO.newBuilder();
            role_info.setName(name);
            role_info.setAreaId(areaId);
            response.setRoleInfo(role_info);

            if (op.getResult() == 1) {
                if (op.getUId() == null) {
                    ServerTag tag = new ServerTag().setType(ServerType.Manager).setAreaId(Context.tag.getAreaId());
                    AbstractSession managerSession = ManagerServerSessionMng.getInstance().getRegisterSession(tag);
                    if (managerSession == null) {
                        response.setResult(ErrorCode.ServerNotOpen.getValue());
                        clientSession.sendMessage(response.build());
                        return;
                    }
                    clientSession.reqCreateMsg = msg;

                    //获取最大uid
                    G2M.MSG_G2M_MAX_UID.Builder request = G2M.MSG_G2M_MAX_UID.newBuilder();
                    request.setUsername(clientSession.getAccountPOJO().getUsername());
                    request.setAreaId(Context.tag.getAreaId());
                    managerSession.sendMessage(request.build());
                } else if (op.getUId() > 0) {
                    response.setResult(ErrorCode.DuplicatedName.getValue());
                    //DuplicatedName
                    clientSession.sendMessage(response.build());
                }
            } else {
                log.error("db error:get role UId by name operator fail");
            }
        });
    }

}

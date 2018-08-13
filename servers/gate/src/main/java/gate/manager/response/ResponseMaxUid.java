package gate.manager.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.model.ClientTag;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gamedb.dao.role.CreateRoleDBOperator;
import gamedb.pojo.RolePOJO;
import gate.Context;
import gate.GateService;
import gate.client.ClientSession;
import gate.client.ClientSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.client.Client.*;
import protocol.manager.gate.M2G;

@Slf4j
public class ResponseMaxUid implements IResponseHandler {

    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        M2G.MSG_M2G_MAX_UID msg = M2G.MSG_M2G_MAX_UID.parseFrom(packet.getMsg());
        //TODO:BOIL 正式开始创建角色
        createRole(msg);
    }

    private void createRole(M2G.MSG_M2G_MAX_UID msg) {
        int result = msg.getResult();
//        if (msg.getResult() !=ErrorCode.SUCCESS.getValue())
//        {
//            log.warn("account {} create role failed: no watch dog manager", session.getAccountPOJO().getUsername());
//            response.setResult(ErrorCode.ServerNotOpen.getValue());
//            session.sendMessage(response.build());
//            return;
//        }
        if (result != ErrorCode.SUCCESS.getValue()) {
            log.error("got max Uid got an error: {} maybe server not open", ErrorCode.getEnum(result));
            return;
        }

        int maxUid = msg.getMaxUid();

        String username = msg.getUsername();
        String channel = msg.getChannelName();
        ClientTag clientTag = new ClientTag().setUsername(username).setChannelName(channel);

        ClientSession clientSession = (ClientSession) ClientSessionMng.getInstance().getRegisterSession(clientTag);
        if (clientSession == null) {
            log.error("account {} channel {} got an error: session is null", username, channel);
            return;
        }


        if (clientSession.reqCreateMsg == null) {
            MSG_GC_CREATE_ROLE.Builder response = MSG_GC_CREATE_ROLE.newBuilder();
            log.warn("account {0} create role failed: ReqCreateMsg is null", clientSession.getAccountPOJO().getUsername());
            response.setResult(ErrorCode.NotCreating.getValue());
            clientSession.sendMessage(response.build());
            return;
        }
//        //try to insert role name
//        InsertRoleNameDBOperator operator=new InsertRoleNameDBOperator(clientSession.getAccountPOJO().getUsername(), maxUid);
//        GateService.context.db.Call(operator,ret -> {
//            InsertRoleNameDBOperator op=(InsertRoleNameDBOperator)ret;
//            if(op.getResult()!=1){
//                MSG_GC_CREATE_ROLE.Builder response= MSG_GC_CREATE_ROLE.newBuilder();
//                response.setRoleInfo(clientSession.reqCreateMsg.getRoleInfo());
//                //DuplicatedName
//                response.setResult(ErrorCode.DuplicatedName.getValue());
//                clientSession.sendMessage(response.build());
//            }
//            else {
//                createRoleWithTransaction(clientSession);
//            }
//        });
        //Create role
        createRoleWithTransaction(clientSession, maxUid);
    }

    private void createRoleWithTransaction(ClientSession session, int uid) {
        RolePOJO pojo = new RolePOJO();
        pojo.setUid(uid);
        pojo.setUsername(session.getAccountPOJO().getUsername());
        pojo.setChannelName(session.getAccountPOJO().getChannelName());
        pojo.setGroupId(Context.tag.getGroupId());
        //1 映射创建信息到pojo
        ROLE_INFO role_info = session.reqCreateMsg.getRoleInfo();
        pojo.setRoleName(role_info.getName());
        pojo.setSex(role_info.getSex());
        pojo.setFaceIconId(role_info.getFaceIconId());

        //2 加载初始化角色信息
        loadDefaultInfo(pojo);
        //3 更新到db
        //try to insert role info to db
        //String tableName=GateService.context.db.GetTableName("role",response.getCharacterInfo().getUid() ,DBTableParamType.Character);
        CreateRoleDBOperator operator = new CreateRoleDBOperator(pojo);
        GateService.context.db.Call(operator, ret -> {
            CreateRoleDBOperator op = (CreateRoleDBOperator) ret;
            //回调内容
            MSG_GC_CREATE_ROLE.Builder response = MSG_GC_CREATE_ROLE.newBuilder();
            if (op.getResult() == 1) {
                ROLE_INFO.Builder roleInfoBuilder = role_info.toBuilder();
                roleInfoBuilder.setUid(op.getRole().getUid());
                roleInfoBuilder.setName(op.getRole().getRoleName());
                roleInfoBuilder.setFaceIconId(op.getRole().getFaceIconId());
                roleInfoBuilder.setSex(op.getRole().getSex());
                response.setRoleInfo(roleInfoBuilder);
                response.setResult(ErrorCode.SUCCESS.getValue());
                log.error("create role id {} name {} success", op.getRole().getUid(), op.getRole().getRoleName());
            } else {
                response.setRoleInfo(role_info);
                response.setResult(ErrorCode.NotCreating.getValue());
                log.error("create role {} fail", op.getRole().getRoleName());
            }
            session.sendMessage(response.build());
        });
    }

    private void loadDefaultInfo(RolePOJO pojo) {
        //TODO:BOIL 创角默认初始信息加载

    }
}

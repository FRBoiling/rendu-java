package gate.manager.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.model.ClientTag;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gamedb.dao.accountRoleList.InsertAccountRoleDBOperator;
import gamedb.dao.role.CreateRoleDBOperator;
import gamedb.pojo.role.RolePOJO;
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
        //正式开始创建角色
        createRole(msg);
    }

    private void createRole(M2G.MSG_M2G_MAX_UID msg) {
        int result = msg.getResult();
//        if (msg.getResult() !=ErrorCode.SUCCESS.getValue())
//        {
//            log.warn("account {} create player failed: no watch dog manager", session.getAccountPOJO().getUsername());
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
        int areaId = msg.getAreaId();
        ClientTag clientTag = new ClientTag().setUsername(username).setAreaId(areaId);

        ClientSession clientSession = (ClientSession) ClientSessionMng.getInstance().getRegisterSession(clientTag);
        if (clientSession == null) {
            log.error("account {} areaId {} got an error: session is null", username, areaId);
            return;
        }


        if (clientSession.reqCreateMsg == null) {
            MSG_GC_CREATE_ROLE.Builder response = MSG_GC_CREATE_ROLE.newBuilder();
            log.warn("account {0} create role failed: ReqCreateMsg is null", clientSession.getAccountPOJO().getUsername());
            response.setResult(ErrorCode.NotCreating.getValue());
            clientSession.sendMessage(response.build());
            return;
        }
//        //try to insert player name
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
        //Create player
        createRoleWithTransaction(clientSession, maxUid);
    }

    private void createRoleWithTransaction(ClientSession session, int uid) {
        RolePOJO pojo = new RolePOJO();
        pojo.setUid(uid);
        pojo.setUsername(session.getAccountPOJO().getUsername());
        pojo.setAreaId(Context.tag.getAreaId());
        pojo.setChannelName("default channel");
        //1 映射创建信息到pojo
        pojo.setRoleName(session.reqCreateMsg.getName());
        pojo.setAreaId(session.reqCreateMsg.getAreaId());

        //2 加载初始化角色信息
        loadDefaultInfo(pojo);
        //3 更新到db
        //try to insert player info to db
        //String tableName=GateService.context.db.GetTableName("player",response.getCharacterInfo().getUid() ,DBTableParamType.Character);
        CreateRoleDBOperator operator = new CreateRoleDBOperator(pojo);
        GateService.context.db.Call(operator, ret -> {
            CreateRoleDBOperator op = (CreateRoleDBOperator) ret;
            //回调内容
            MSG_GC_CREATE_ROLE.Builder response = MSG_GC_CREATE_ROLE.newBuilder();

            if (op.getResult() == 1) {
                ROLE_INFO.Builder roleInfoBuilder = ROLE_INFO.newBuilder();
                roleInfoBuilder.setUid(op.getRole().getUid());
                roleInfoBuilder.setName(op.getRole().getRoleName());
                roleInfoBuilder.setAreaId(op.getRole().getAreaId());
                response.setRoleInfo(roleInfoBuilder);
                response.setResult(ErrorCode.SUCCESS.getValue());

                GateService.context.db.Call(new InsertAccountRoleDBOperator(op.getRole()));
                        //
                log.info("create role id {} name {} success", op.getRole().getUid(), op.getRole().getRoleName());
            } else {
                ROLE_INFO.Builder roleInfoBuilder = ROLE_INFO.newBuilder();
                roleInfoBuilder.setName(op.getRole().getRoleName());
                roleInfoBuilder.setAreaId(op.getRole().getAreaId());
                response.setRoleInfo(roleInfoBuilder);
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

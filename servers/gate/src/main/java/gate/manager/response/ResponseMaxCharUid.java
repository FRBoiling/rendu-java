package gate.manager.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.model.ClientTag;
import core.base.model.ServerTag;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gamedb.dao.role.CreateRoleDBOperator;
import gamedb.dao.role.InsertRoleNameDBOperator;
import gamedb.pojo.RolePOJO;
import gate.GateService;
import gate.client.ClientSession;
import gate.client.ClientSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.client.Client.*;
import protocol.manager.gate.M2G;

@Slf4j
public class ResponseMaxCharUid implements IResponseHandler {

    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        M2G.MSG_M2G_MAX_UID msg=M2G.MSG_M2G_MAX_UID.parseFrom(packet.getMsg());
        String account=msg.getUsername();
        String channel=msg.getChannelName();
        int result =msg.getResult();
        int maxUid=msg.getMaxUid();

        ClientTag clientTag = new ClientTag().setChannelName(account).setChannelName(channel);
        AbstractSession  clientSession = ClientSessionMng.getInstance().getRegisterSession(clientTag);

        //TODO:BOIL 正式开始创建角色
        createRole(maxUid,(ClientSession) clientSession);
    }

    private void createRole(int uid, ClientSession session) {
        MSG_GC_CREATE_ROLE.Builder response= MSG_GC_CREATE_ROLE.newBuilder();
        if (session.reqCreateMsg == null)
        {
            log.warn("account {0} create role failed: ReqCreateMsg is null", session.getAccountPOJO().getUsername());
            response.setResult(ErrorCode.NotCreating.getValue());
            session.sendMessage(response.build());
            return;
        }
//        if (msg.getResult() !=ErrorCode.SUCCESS.getValue())
//        {
//            log.warn("account {} create role failed: no watch dog manager", session.getAccountPOJO().getUsername());
//            response.setResult(ErrorCode.ServerNotOpen.getValue());
//            session.sendMessage(response.build());
//            return;
//        }

        ROLE_INFO.Builder role_info = ROLE_INFO.newBuilder();
        role_info.setUid(uid);
        response.setRoleInfo(role_info);

       // try to insert role name
        InsertRoleNameDBOperator operator=new InsertRoleNameDBOperator(session.getAccountPOJO().getUsername(), uid);
        GateService.context.db.Call(operator,operator1 -> {
            InsertRoleNameDBOperator op=(InsertRoleNameDBOperator)operator1;
            if(op.getResult()!=1){
                //DuplicatedName
                response.setResult(ErrorCode.DuplicatedName.getValue());
                session.sendMessage(response.build());
            }
            else {
                //Create role
                createRoleWithTransaction(session,response.build());
            }
        });
    }

    private void createRoleWithTransaction(ClientSession session, MSG_GC_CREATE_ROLE response) {
        RolePOJO pojo=new RolePOJO();
        //String tableName=GateService.context.db.GetTableName("role",response.getCharacterInfo().getUid() ,DBTableParamType.Character);
        pojo.setTableName("role");

        //TODO:设置各种数据到pojo

        CreateRoleDBOperator operator=new CreateRoleDBOperator(pojo);
        GateService.context.db.Call(operator,(op)-> {
            //回调内容
            //TODO 所有的需要的默认角色信息，插入到DB
        });
    }
}

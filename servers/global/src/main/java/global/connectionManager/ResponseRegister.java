package global.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.Errorcode;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import global.GlobalServiceContext;
import global.gate.GateServerSessionMng;
import global.manager.ManagerServerSessionMng;
import global.relation.RelationServerSessionMng;
import global.zone.ZoneServerSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-29
 * Time: 20:28
 */

@Slf4j
public class ResponseRegister implements IResponseHandler {
    @Override
    public void onResponse(byte[] message, AbstractSession session) throws InvalidProtocolBufferException {
        ServerRegister.MSG_REQ_Server_Register msg = ServerRegister.MSG_REQ_Server_Register.parseFrom(message);
        ServerType serverType = ServerType.values()[msg.getServerType()];
        //基本信息注册 MSG_REQ_Server_Register
        ServerTag tag = new ServerTag();
        tag.setTag(serverType,msg.getGroupId(),msg.getSubId());
        session.setTag(tag);

        //注册反馈 MSG_RES_Server_Register
        ServerRegister.MSG_RES_Server_Register.Builder response = ServerRegister.MSG_RES_Server_Register.newBuilder();
        response.setServerType(GlobalServiceContext.tag.getType().ordinal());
        response.setGroupId(GlobalServiceContext.tag.getGroupId());
        response.setSubId(GlobalServiceContext.tag.getSubId());

        boolean isRegisterSuccess =false;
        switch (serverType) {
            case Gate:
                isRegisterSuccess = GateServerSessionMng.getInstance().register(session);
                break;
            case Manager:
                isRegisterSuccess = ManagerServerSessionMng.getInstance().register(session);
                break;
            case Zone:
                isRegisterSuccess = ZoneServerSessionMng.getInstance().register(session);
                break;
            case Relation:
                isRegisterSuccess = RelationServerSessionMng.getInstance().register(session);
                break;
            default:
                break;
        }

        if (isRegisterSuccess) {
            //TODO:这里添加具体注册逻辑
            response.setResult(Errorcode.Success.ordinal());
        } else {
            response.setResult(Errorcode.Fail.ordinal());
        }
        //发送反馈信息
        session.sendMessage(response.build());
    }
}

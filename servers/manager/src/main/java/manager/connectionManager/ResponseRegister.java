package manager.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import lombok.extern.slf4j.Slf4j;
import manager.ManagerServiceContext;
import manager.zone.ZoneServerSessionMng;
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
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ServerRegister.MSG_Server_Register msg = ServerRegister.MSG_Server_Register.parseFrom(packet.getMsg());

        //基本信息注册 MSG_Server_Register
        ServerType serverType = ServerType.values()[msg.getTag().getServerType()];
        int groupId = msg.getTag().getGroupId();
        int subId = msg.getTag().getSubId();

        ServerTag tag = new ServerTag();
        tag.setTag(serverType,groupId,subId);
        session.setTag(tag);

        //注册反馈 MSG_Server_Register_Return
        ServerRegister.Server_Tag.Builder serverTag = ServerRegister.Server_Tag.newBuilder();
        serverTag.setServerType(ManagerServiceContext.tag.getType().ordinal());
        serverTag.setGroupId(ManagerServiceContext.tag.getGroupId());
        serverTag.setSubId(ManagerServiceContext.tag.getSubId());

        ServerRegister.MSG_Server_Register_Return.Builder response = ServerRegister.MSG_Server_Register_Return.newBuilder();
        response.setTag(serverTag);

        boolean isRegisterSuccess =false;
        switch (serverType) {
//            case Gate:
//                isRegisterSuccess = GateServerSessionMng.getInstance().register(session);
//                break;
//            case Manager:
//                isRegisterSuccess = ManagerServerSessionMng.getInstance().register(session);
//                break;
            case Zone:
                isRegisterSuccess = ZoneServerSessionMng.getInstance().register(session);
                break;
//            case Relation:
//                isRegisterSuccess = RelationServerSessionMng.getInstance().register(session);
//                break;
            default:
                break;
        }

        if (isRegisterSuccess) {
            response.setResult(ErrorCode.Success.ordinal());
        } else {
            response.setResult(ErrorCode.Fail.ordinal());
        }
        //发送反馈信息
        session.sendMessage(response.build());
    }
}

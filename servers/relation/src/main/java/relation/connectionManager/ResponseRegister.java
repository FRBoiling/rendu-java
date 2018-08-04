package relation.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.RegisterResult;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;
import relation.Context;
import relation.relation.RelationServerSessionMng;
import relation.zone.ZoneServerSessionMng;

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
        serverTag.setServerType(Context.tag.getType().ordinal());
        serverTag.setGroupId(Context.tag.getGroupId());
        serverTag.setSubId(Context.tag.getSubId());

        ServerRegister.MSG_Server_Register_Return.Builder response = ServerRegister.MSG_Server_Register_Return.newBuilder();
        response.setTag(serverTag);

        RegisterResult registerResult =RegisterResult.FAIL;
        switch (serverType) {
            case Zone:
                if (Context.tag.getGroupId() == groupId){
                    registerResult = ZoneServerSessionMng.getInstance().register(session);
                }
                else  {

                }
                break;
            case Relation:
                if (Context.tag.getGroupId() < groupId ){
                    registerResult = RelationServerSessionMng.getInstance().register(session);
                }
                else {

                }
                break;
            default:
               break;
        }

        response.setResult(registerResult.ordinal());
        //发送反馈信息
        session.sendMessage(response.build());
    }
}

package global.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.RegisterResult;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import global.Context;
import global.gate.GateServerSessionMng;
import global.manager.ManagerServerSessionMng;
import global.relation.RelationServerSessionMng;
import global.zone.ZoneServerSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;

import java.util.HashMap;

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
        int areaId = msg.getTag().getAreaId();
        int subId = msg.getTag().getSubId();

        ServerTag tag = new ServerTag();
        tag.setTag(serverType,areaId,subId);
        session.setTag(tag);

        //注册反馈 MSG_Server_Register_Return
        ServerRegister.Server_Tag.Builder serverTag = ServerRegister.Server_Tag.newBuilder();
        serverTag.setServerType(Context.tag.getType().ordinal());
        serverTag.setAreaId(Context.tag.getAreaId());
        serverTag.setSubId(Context.tag.getSubId());

        log.debug("{} got register info from {}",Context.tag.toString(),tag.toString());

        ServerRegister.MSG_Server_Register_Return.Builder response = ServerRegister.MSG_Server_Register_Return.newBuilder();
        response.setTag(serverTag);

        RegisterResult registerResult =RegisterResult.FAIL;
        switch (serverType) {
            case Gate:
                registerResult = GateServerSessionMng.getInstance().register(session);
                break;
            case Manager:
                registerResult = ManagerServerSessionMng.getInstance().register(session);
                break;
            case Zone:
                registerResult = ZoneServerSessionMng.getInstance().register(session);
                break;
            case Relation:
                registerResult = RelationServerSessionMng.getInstance().register(session);
                break;
            default:
                break;
        }

        switch (registerResult) {
            case SUCCESS:
                if (msg.getListenInfosList()!=null){
                    String ip = session.getIP();
                    for (ServerRegister.LISTEN_INFO info: msg.getListenInfosList()) {
                        ServerRegister.Connect_Info.Builder connect_info = ServerRegister.Connect_Info.newBuilder();
                        connect_info.setIp(ip);
                        connect_info.setPort(info.getPort());
                        ServerType type = ServerType.values()[info.getServerType()];
                        ListenInfoMng.getInstance().setRegisteredConnectInfo(tag, type ,connect_info.build());
                    }
                }
                Context.connectMng.sendConnectionCommand(tag);
                break;
            case FAIL:
            case REPEATED_REGISTER:
            default:
                break;
        }
        response.setResult(registerResult.ordinal());
        //发送反馈信息
        session.sendMessage(response.build());
    }
}

package manager.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import lombok.extern.slf4j.Slf4j;
import manager.ManagerService;
import manager.global.GlobalServerSessionMng;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-07
 * Time: 16:42
 */
@Slf4j
public class ResponseRegisterReturn implements IResponseHandler{
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ServerRegister.MSG_Server_Register_Return msg = ServerRegister.MSG_Server_Register_Return.parseFrom(packet.getMsg());

        //注册反馈 MSG_Server_Register_Return
        ServerType serverType = ServerType.values()[msg.getTag().getServerType()];
        int groupId = msg.getTag().getGroupId();
        int subId = msg.getTag().getSubId();

        ServerTag tag = new ServerTag();
        tag.setTag(serverType,groupId,subId);
        session.setTag(tag);


        if ( msg.getResult() == ErrorCode.Success.ordinal())
        {
            boolean isRegisterSuccess =false;
            switch (serverType) {
                case Global:
                    isRegisterSuccess = GlobalServerSessionMng.getInstance().register(session);
                    break;
//                case Gate:
//                  isRegisterSuccess = GateServerSessionMng.getInstance().register(session);
                default:
                    break;
            }
            log.info("register to {} {}",tag.getStrTag(),ErrorCode.values()[msg.getResult()]);
            if (isRegisterSuccess) {
                //TODO:这里添加具体注册逻辑
            } else {
                if ( serverType == ServerType.Global){
                    ManagerService.context.stop();
                }
                log.error("register to {} fail: {}",tag.getStrTag(),ErrorCode.values()[msg.getResult()]);
            }
        }else {
            if ( serverType == ServerType.Global){
                ManagerService.context.stop();
            }
            log.error("register to {} fail: {}",tag.getStrTag(),ErrorCode.values()[msg.getResult()]);
        }
    }
}

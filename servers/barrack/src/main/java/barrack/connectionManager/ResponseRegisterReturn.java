package barrack.connectionManager;

import barrack.BarrackService;
import barrack.global.GlobalServerSessionMng;
import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-13
 * Time: 09:59
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
                case Zone:
//                    isRegisterSuccess = ClientServerSessionMng.getInstance().register(session);
                    break;
                default:
                    break;
            }
            if (isRegisterSuccess) {
                log.info("register to {} success: {}",tag.getStrTag(),ErrorCode.values()[msg.getResult()]);
                //TODO:这里添加具体注册逻辑
            } else {
                if (serverType ==ServerType.Global){
                    BarrackService.context.stop();
                }
                log.error("register to {} fail :{}",tag.getStrTag(),ErrorCode.values()[msg.getResult()]);
            }
        }else {
            if (serverType ==ServerType.Global){
                BarrackService.context.stop();
            }
            log.error("register to {} fail :{}",tag.getStrTag(),ErrorCode.values()[msg.getResult()]);
        }
    }
}

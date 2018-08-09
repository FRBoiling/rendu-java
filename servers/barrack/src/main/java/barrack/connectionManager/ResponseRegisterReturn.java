package barrack.connectionManager;

import barrack.BarrackService;
import barrack.global.GlobalServerSessionMng;
import com.google.protobuf.InvalidProtocolBufferException;
import constant.RegisterResult;
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

        if ( msg.getResult() == RegisterResult.SUCCESS.ordinal())
        {
            RegisterResult registerResult =RegisterResult.SUCCESS ;
            switch (serverType) {
                case Global:
                    registerResult = GlobalServerSessionMng.getInstance().register(session);
                    break;
                case Zone:
//                    registerResult = ClientServerSessionMng.getInstance().registerHandler(session);
                    break;
                default:
                    break;
            }
            switch (registerResult) {
                case SUCCESS:
                    //TODO:这里添加具体注册逻辑
                    break;
                case REPEATED_REGISTER:
                case FAIL:
                default:
                    log.error("SERIOUS ERROR: get registerHandler result from {} success ,but registerHandler here fail :{} ", tag.getStrTag(),registerResult.toString() );
                    BarrackService.context.stop();
                    break;
            }
        }else {
            log.error("SERIOUS ERROR: registerHandler result from {} fail :{}", tag.getStrTag(), RegisterResult.values()[msg.getResult()]);
            switch (serverType) {
                case Global:
                    BarrackService.context.stop();
                    break;
                default:
                    break;
            }
        }
    }
}

package gate.connectionmanager;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.Errorcode;
import core.base.common.AbstractSession;
import core.base.concurrent.AbstractHandler;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import gate.global.GlobalServerSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-07
 * Time: 16:42
 */
@Slf4j
public class Response_Res_Register extends AbstractHandler<byte[]> {
    @Override
    public void doAction() throws InvalidProtocolBufferException {
        AbstractSession session = (AbstractSession) this.session;

        ServerRegister.MSG_RES_Server_Register msg = ServerRegister.MSG_RES_Server_Register.parseFrom(this.message);

        ServerTag tag = new ServerTag();

        ServerType serverType = ServerType.values()[msg.getServerType()];
        //基本信息注册 MSG_REQ_Server_Register
        Object[] params = new Object[]{
                serverType,
                msg.getGroupId(),
                msg.getSubId()
        };
        tag.initTag(params);

        if ( msg.getResult() == Errorcode.Success.ordinal())
        {
            boolean isRegisterSuccess =false;
            switch (serverType) {
                case Global:
                    isRegisterSuccess = GlobalServerSessionMng.getInstance().register(session);
                    break;
                case Gate:
//                  isRegisterSuccess = GateServerSessionMng.getInstance().register(session);
                default:
                    break;
            }
            log.info("register to {} {}",tag.getStrTag(),Errorcode.values()[msg.getResult()]);
            if (isRegisterSuccess) {
                //TODO:这里添加具体注册逻辑
            } else {
            }
        }else {
            log.error("register to {} fail: {}",tag.getStrTag(),Errorcode.values()[msg.getResult()]);
        }
    }
}

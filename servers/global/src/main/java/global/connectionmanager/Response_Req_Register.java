package global.connectionmanager;

import com.google.protobuf.InvalidProtocolBufferException;
import common.constant.Errorcode;
import core.base.common.AbstractSession;
import core.base.concurrent.queue.AbstractHandler;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import global.GlobalServiceContext;
import global.gate.GateServerSessionMng;
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
public class Response_Req_Register extends AbstractHandler<byte[]> {
    @Override
    public void doAction() throws InvalidProtocolBufferException {
        AbstractSession session = (AbstractSession) this.session;

        ServerRegister.MSG_REQ_Server_Register msg = ServerRegister.MSG_REQ_Server_Register.parseFrom(this.message);
        ServerTag tag = new ServerTag();

        ServerType serverType = ServerType.values()[msg.getServerType()];
        //基本信息注册 MSG_REQ_Server_Register
        Object[] params = new Object[]{
                serverType,
                msg.getGroupId(),
                msg.getSubId()
        };
        tag.initTag(params);

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
            case Global:
//                isRegisterSuccess = GlobalServerSessionMng.getInstance().register(session);
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

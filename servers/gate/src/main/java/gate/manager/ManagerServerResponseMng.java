package gate.manager;

import core.network.IResponseHandlerManager;
import gate.connectionManager.ResponseRegisterReturn;
import gate.manager.response.ResponseMaxCharUid;
import protocol.manager.gate.M2G;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

public class ManagerServerResponseMng implements IResponseHandlerManager {
    ManagerServerResponseMng() {
        register();
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register_Return.class), ResponseRegisterReturn.class);

        //创建角色
        register(Id.getInst().getMessageId(M2G.MSG_M2G_MAX_UID.class), ResponseMaxCharUid.class);
    }
}

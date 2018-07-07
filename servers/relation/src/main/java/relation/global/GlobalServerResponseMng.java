package relation.global;

import core.network.IResponseHandlerManager;

import protocol.msgId.Id;
import protocol.server.register.ServerRegister;
import relation.connectionManager.ResponseRegisterReturn;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

public class GlobalServerResponseMng implements IResponseHandlerManager {
    public GlobalServerResponseMng() {
        register();
    }

    @Override
    public void register() {
       register(Id.getInst().getMessageId(ServerRegister.MSG_RES_Server_Register.class), ResponseRegisterReturn.class);
    }
}

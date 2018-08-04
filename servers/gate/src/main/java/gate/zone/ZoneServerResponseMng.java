package gate.zone;

import core.network.IResponseHandlerManager;
import gate.connectionManager.ResponseRegisterReturn;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

public class ZoneServerResponseMng implements IResponseHandlerManager {
    ZoneServerResponseMng() {
        register();
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register_Return.class), ResponseRegisterReturn.class);
    }
}
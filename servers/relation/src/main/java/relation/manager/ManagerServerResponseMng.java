package relation.manager;

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

public class ManagerServerResponseMng implements IResponseHandlerManager {

    private static ManagerServerResponseMng INSTANCE = new ManagerServerResponseMng();

    public static ManagerServerResponseMng getInstance() {
        return INSTANCE;
    }

    private ManagerServerResponseMng() {
        registerHandlers();
    }

    @Override
    public void registerHandlers() {
        registerHandler(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register_Return.class), ResponseRegisterReturn.class);
    }
}

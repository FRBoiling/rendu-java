package barrack.global;

import barrack.connectionManager.ResponseConnectCommand;
import barrack.connectionManager.ResponseRegisterReturn;
import core.network.IResponseHandlerManager;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

public class GlobalServerResponseMng implements IResponseHandlerManager {

    private static GlobalServerResponseMng INSTANCE = new GlobalServerResponseMng();

    public static GlobalServerResponseMng getInstance() {
        return INSTANCE;
    }

    private GlobalServerResponseMng() {
        registerHandlers();
    }

    @Override
    public void registerHandlers() {
        registerHandler(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register_Return.class), ResponseRegisterReturn.class);
        registerHandler(Id.getInst().getMessageId(ServerRegister.MSG_Server_Connect_Command.class), ResponseConnectCommand.class);
    }
}

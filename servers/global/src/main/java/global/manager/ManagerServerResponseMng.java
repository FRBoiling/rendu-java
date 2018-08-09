package global.manager;

import core.network.IResponseHandlerManager;
import global.connectionManager.ResponseRegister;
import global.manager.response.ResponseHeartBeat;
import lombok.extern.slf4j.Slf4j;
import protocol.manager.global.M2GM;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-29
 * Time: 16:26
 */
@Slf4j
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
        registerHandler(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register.class), ResponseRegister.class);
        registerHandler(Id.getInst().getMessageId(M2GM.MSG_M2GM_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

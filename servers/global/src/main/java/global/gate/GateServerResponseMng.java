package global.gate;

import core.network.IResponseHandlerManager;
import global.connectionManager.ResponseRegister;
import global.gate.response.ResponseHeartBeat;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.global.G2GM;
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
public class GateServerResponseMng implements IResponseHandlerManager {

    private static GateServerResponseMng INSTANCE = new GateServerResponseMng();

    public static GateServerResponseMng getInstance() {
        return INSTANCE;
    }

    private GateServerResponseMng() {
        registerHandlers();
    }

    @Override
    public void registerHandlers() {
        registerHandler(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register.class), ResponseRegister.class);
        registerHandler(Id.getInst().getMessageId(G2GM.MSG_G2GM_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

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

    public GateServerResponseMng() {
        register();
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register.class), ResponseRegister.class);
        register(Id.getInst().getMessageId(G2GM.MSG_G2GM_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

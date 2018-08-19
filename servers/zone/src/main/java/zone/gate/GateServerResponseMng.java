package zone.gate;

import core.network.IResponseHandlerManager;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.zone.G2Z;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;
import zone.connectionManager.ResponseRegister;
import zone.gate.response.ResponseHeartBeat;

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
        registerHandler(Id.getInst().getMessageId(G2Z.MSG_G2Z_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

package manager.gate;

import core.network.IResponseHandlerManager;
import lombok.extern.slf4j.Slf4j;
import manager.connectionManager.ResponseRegister;
import manager.gate.response.ResponseMaxUid;
import manager.relation.response.ResponseHeartBeat;
import protocol.gate.manager.G2M;
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
        registerHandler(Id.getInst().getMessageId(G2M.MSG_G2M_HEARTBEAT.class), ResponseHeartBeat.class);

        //创角
        registerHandler(Id.getInst().getMessageId(G2M.MSG_G2M_MAX_UID.class), ResponseMaxUid.class);
    }
}

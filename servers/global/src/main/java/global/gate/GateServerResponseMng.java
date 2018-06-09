package global.gate;

import core.network.IResponseHandlerManager;
import global.connectionmanager.Response_Req_Register;
import global.gate.response.Response_HeartBeat;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.global.G2GM;
import protocol.gate.global.G2GMIdGenerater;
import protocol.global.gate.GM2GIdGenerater;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;
import protocol.server.register.ServerRegisterIdGenerater;

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
        ServerRegisterIdGenerater.GenerateId();
        G2GMIdGenerater.GenerateId();
        GM2GIdGenerater.GenerateId();
        register();
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(ServerRegister.MSG_REQ_Server_Register.class), Response_Req_Register.class);
        register(Id.getInst().getMessageId(G2GM.MSG_G2GM_HEARTBEAT.class), Response_HeartBeat.class);
    }
}

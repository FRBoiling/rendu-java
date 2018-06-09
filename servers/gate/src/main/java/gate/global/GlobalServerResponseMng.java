package gate.global;

import core.network.IResponseHandlerManager;
import gate.connectionmanager.Response_Res_Register;
import protocol.gate.global.G2GMIdGenerater;
import protocol.global.gate.GM2G;
import protocol.global.gate.GM2GIdGenerater;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;
import protocol.server.register.ServerRegisterIdGenerater;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

public class GlobalServerResponseMng implements IResponseHandlerManager {
    public GlobalServerResponseMng() {
        ServerRegisterIdGenerater.GenerateId();
        GM2GIdGenerater.GenerateId();
        G2GMIdGenerater.GenerateId();
        register();
    }

    @Override
    public void register() {
       register(Id.getInst().getMessageId(ServerRegister.MSG_RES_Server_Register.class), Response_Res_Register.class);
    }
}

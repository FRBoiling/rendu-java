package gate.global;

import core.network.IResponseHandlerManager;
import gate.global.response.Response_GateRegister_Res;
import protocol.gate.global.G2GMIdGenerater;
import protocol.global.gate.GM2G;
import protocol.global.gate.GM2GIdGenerater;
import protocol.msgId.Id;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

public class GlobalServerResponseMng implements IResponseHandlerManager {
    public GlobalServerResponseMng() {
        GM2GIdGenerater.GenerateId();
        G2GMIdGenerater.GenerateId();
        register();
    }

    @Override
    public void register() {
       register(Id.getInst().getMessageId(GM2G.MSG_GM2G_RES_Register.class), Response_GateRegister_Res.class);
    }
}

package gate.global;

import core.network.IMessageAndHandler;
import protocol.gate.global.G2GMIdGenerater;
import protocol.global.gate.GM2GIdGenerater;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

public class GlobalServerResponseMng implements IMessageAndHandler {
    public GlobalServerResponseMng() {
        GM2GIdGenerater.GenerateId();
        G2GMIdGenerater.GenerateId();
        register();
    }

    @Override
    public void register() {
//        register(Id.getInst().getMessageId(G2GM.MSG_GM2G_REQ_Register.class), Response_Register.class);
    }
}

package global.gate;

import core.base.concurrent.command.AbstractHandler;
import core.network.IMessageAndHandler;
import global.gate.response.Response_Register;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.global.G2GM;
import protocol.gate.global.G2GMIdGenerater;
import protocol.msgId.Id;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-29
 * Time: 16:26
 */
@Slf4j
public class GateServerResponseMng implements IMessageAndHandler {
    private final HashMap<Integer, Class<? extends AbstractHandler>> handlers = new HashMap<>(10);

    public GateServerResponseMng() {
        G2GMIdGenerater.GenerateId();
        register();
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(G2GM.MSG_G2GM_REQ_Register.class), Response_Register.class);
    }
}

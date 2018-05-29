package global.gate;

import core.base.concurrent.command.AbstractHandler;
import core.network.IMessageAndHandler;
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
public class GateResponserMng implements IMessageAndHandler {
    private final HashMap<Integer, Class<? extends AbstractHandler>> handlers = new HashMap<>(10);

    public GateResponserMng() {
        G2GMIdGenerater.GenerateId();
        register();
    }

    @Override
    public AbstractHandler getHandler(int messageId) {
        Class<? extends AbstractHandler> clazz = handlers.get(messageId);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void register(int messageId, Class<? extends AbstractHandler> handler) {
        if (handlers.containsKey(messageId)) {
            log.debug("add handler failed: msgId {0}", messageId);
            return;
        }
        handlers.put(messageId, handler);
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(G2GM.MSG_GM2G_REQ_Register.class), Response_Register.class);
    }
}

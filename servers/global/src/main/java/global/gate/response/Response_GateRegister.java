package global.gate.response;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.concurrent.queue.AbstractHandler;
import global.gate.GateServerSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.global.G2GM;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-29
 * Time: 20:28
 */

@Slf4j
public class Response_GateRegister extends AbstractHandler<byte[]> {
    @Override
    public void doAction() throws InvalidProtocolBufferException {
        AbstractSession session = (AbstractSession) this.session;

        G2GM.MSG_G2GM_REQ_Register msg = G2GM.MSG_G2GM_REQ_Register.parseFrom(this.message);
        int id = msg.getId();
        session.setKey("GateServer"+id);
        GateServerSessionMng.getInstance().register(session);
    }
}

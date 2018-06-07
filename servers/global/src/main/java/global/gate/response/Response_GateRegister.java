package global.gate.response;

import core.base.concurrent.command.AbstractHandler;
import lombok.extern.slf4j.Slf4j;

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
    public void doAction() {
//        Session session = (Session) this.session;
        log.info("Response_GateRegister");
//        UserManager.getInstance().login(session, message.getLoginName());
    }
}

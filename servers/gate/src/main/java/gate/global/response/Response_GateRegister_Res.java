package gate.global.response;

import core.base.concurrent.command.AbstractHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-07
 * Time: 16:42
 */
@Slf4j
public class Response_GateRegister_Res extends AbstractHandler<byte[]> {
    @Override
    public void doAction() {
//        Session session = (Session) this.session;
        log.info("Response_GateRegister_Res");
//        UserManager.getInstance().login(session, message.getLoginName());
    }
}

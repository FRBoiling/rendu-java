package server.system.user.handler;

import core.network.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import server.Session;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 17:54
 */
@Slf4j
public class TestHandler extends AbstractHandler<byte[]> {
    @Override
    public void doAction() {
        Session session = (Session) this.session;
        log.info("TestHandler");
        //UserManager.getInstance().login(session, message.getLoginName());
    }
}

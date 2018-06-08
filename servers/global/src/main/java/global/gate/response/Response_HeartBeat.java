package global.gate.response;

import core.base.concurrent.queue.AbstractHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-08
 * Time: 15:56
 */
@Slf4j
public class Response_HeartBeat  extends AbstractHandler<byte[]> {
    @Override
    public void doAction() {
//        Session session = (Session) this.session;
        log.info("Response_HeartBeat");
//        UserManager.getInstance().login(session, message.getLoginName());
    }
}

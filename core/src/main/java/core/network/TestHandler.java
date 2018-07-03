package core.network;

import core.base.concurrent.queue.AbstractHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-03
 * Time: 14:29
 */
@Slf4j
public class TestHandler extends AbstractHandler<byte[]> {
    @Override
    public void doAction() {
        Integer session = (Integer) this.session;
        long sec = session%3 *1000000;
        while (sec>0){
            int a = 12*111;
            int b = a /111;
            sec--;
        }
        log.info("TestHandler  {}",session);
//        UserManager.getInstance().login(session, message.getLoginName());
    }
}
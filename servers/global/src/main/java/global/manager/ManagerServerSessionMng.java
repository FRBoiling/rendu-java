package global.manager;

import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:09
 */

public class ManagerServerSessionMng extends AbstractSessionManager {
    private static volatile ManagerServerSessionMng INSTANCE = new ManagerServerSessionMng();
    private ManagerServerSessionMng(){
    }

    public static ManagerServerSessionMng getInstance() {
        if (INSTANCE == null) {
            synchronized (ManagerServerSessionMng.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ManagerServerSessionMng();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void updateLogic(long dt) {

    }

    @Override
    public AbstractSession createSession(Channel channel) {
        return new ManagerServerSession(channel);
    }
}

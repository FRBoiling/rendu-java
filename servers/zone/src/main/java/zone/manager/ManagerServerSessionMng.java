package zone.manager;

import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:11
 */

public class ManagerServerSessionMng extends AbstractSessionManager {

    private static ManagerServerSessionMng INSTANCE = new ManagerServerSessionMng();

    public static ManagerServerSessionMng getInstance() {
        return INSTANCE;
    }

    private ManagerServerSessionMng() {
    }

    @Override
    public void updateLogic(long dt) {
        //TODO:BOIL some service logic
    }

    @Override
    public AbstractSession createSession(Channel channel) {
        ManagerServerSession session = new ManagerServerSession(channel);
        session.setResponseMng(ManagerServerResponseMng.getInstance());
        return session;
    }
}

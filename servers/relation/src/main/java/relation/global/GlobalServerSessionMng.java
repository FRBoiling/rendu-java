package relation.global;

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

public class GlobalServerSessionMng extends AbstractSessionManager {

    private static GlobalServerSessionMng INSTANCE = new GlobalServerSessionMng();

    public static GlobalServerSessionMng getInstance() {
        return INSTANCE;
    }

    private GlobalServerSessionMng() {
    }

    @Override
    public void updateLogic(long dt) {

    }

    @Override
    public void updateLogic(long dt) {

    }

    @Override
    public AbstractSession createSession(Channel channel) {
        GlobalServerSession session = new GlobalServerSession(channel);
        session.setResponseMng(GlobalServerResponseMng.getInstance());
        return session;
    }
}

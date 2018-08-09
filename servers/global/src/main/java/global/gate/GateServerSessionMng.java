package global.gate;

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

public class GateServerSessionMng extends AbstractSessionManager {

    private static GateServerSessionMng INSTANCE = new GateServerSessionMng();

    public static GateServerSessionMng getInstance() {
        return INSTANCE;
    }

    private GateServerSessionMng() {
    }

    @Override
    public void updateLogic(long dt) {

    }

    @Override
    public void updateLogic(long dt) {

    }

    @Override
    public AbstractSession createSession(Channel channel) {
        GateServerSession session = new GateServerSession(channel);
        session.setResponseMng(GateServerResponseMng.getInstance());
        return session;
    }
}

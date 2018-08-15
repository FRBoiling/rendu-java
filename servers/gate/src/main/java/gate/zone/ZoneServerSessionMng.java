package gate.zone;

import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import gate.Context;
import gate.GateService;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:11
 */

public class ZoneServerSessionMng extends AbstractSessionManager {

    private static ZoneServerSessionMng INSTANCE = new ZoneServerSessionMng();

    public static ZoneServerSessionMng getInstance() {
        return INSTANCE;
    }

    private ZoneServerSessionMng() {
    }

    @Override
    public void updateLogic(long dt) {

    }

    @Override
    public AbstractSession createSession(Channel channel) {
        ZoneServerSession session = new ZoneServerSession(channel);
        session.setResponseMng(ZoneServerResponseMng.getInstance());
        return session;
    }
}

package global.gate;

import core.base.common.AbstractSession;
import core.base.common.ISessionManager;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:09
 */

public class GateServerSessionMng implements ISessionManager {
    @Override
    public void register(AbstractSession session) {

    }

    @Override
    public void unregister(AbstractSession session) {

    }

    @Override
    public AbstractSession createSession(Channel channel) {
        return new GateServerSession(channel);
    }
}

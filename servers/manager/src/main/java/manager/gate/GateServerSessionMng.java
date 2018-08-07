package manager.gate;

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
    private static volatile GateServerSessionMng INSTANCE = new GateServerSessionMng();
    private GateServerSessionMng(){
    }

    public static GateServerSessionMng getInstance() {
        if (INSTANCE == null) {
            synchronized (GateServerSessionMng.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GateServerSessionMng();
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
        return new GateServerSession(channel);
    }
}

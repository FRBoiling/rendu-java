package barrack.global;

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

    private static volatile GlobalServerSessionMng INSTANCE = new GlobalServerSessionMng();
    private GlobalServerSessionMng(){
    }

    public static GlobalServerSessionMng getInstance() {
        if (INSTANCE == null) {
            synchronized (GlobalServerSessionMng.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GlobalServerSessionMng();
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
        return new GlobalServerSession(channel);
    }
}

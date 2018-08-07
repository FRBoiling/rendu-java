package relation.zone;

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

public class ZoneServerSessionMng extends AbstractSessionManager {

    private static volatile ZoneServerSessionMng INSTANCE = new ZoneServerSessionMng();
    private ZoneServerSessionMng(){
    }

    public static ZoneServerSessionMng getInstance() {
        if (INSTANCE == null) {
            synchronized (ZoneServerSessionMng.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ZoneServerSessionMng();
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
        return new ZoneServerSession(channel);
    }
}

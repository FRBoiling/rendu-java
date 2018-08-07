package global.zone;

import com.google.protobuf.MessageLite;
import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import io.netty.channel.Channel;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:09
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

    public void broadcastByGroup(MessageLite msg,int groupId) {
        for (AbstractSession session: getRegisterSessions().values()) {
            if (groupId == ((ServerTag)session.getTag()).getGroupId()){
                session.sendMessage(msg);
            }
        }
    }
}

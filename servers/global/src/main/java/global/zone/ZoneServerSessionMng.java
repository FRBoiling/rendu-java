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
    public void updateLogic(long dt) {

    }

    @Override
    public AbstractSession createSession(Channel channel) {
        ZoneServerSession session = new ZoneServerSession(channel);
        session.setResponseMng(ZoneServerResponseMng.getInstance());
        return session;
    }

    public void broadcastByGroup(MessageLite msg, int groupId) {
        for (AbstractSession session : getRegisterSessions().values()) {
            if (groupId == ((ServerTag) session.getTag()).getGroupId()) {
                session.sendMessage(msg);
            }
        }
    }
}

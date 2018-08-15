package global.relation;

import com.google.protobuf.MessageLite;
import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import core.base.model.ServerTag;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:09
 */

public class RelationServerSessionMng extends AbstractSessionManager {

    private static RelationServerSessionMng INSTANCE = new RelationServerSessionMng();

    public static RelationServerSessionMng getInstance() {
        return INSTANCE;
    }

    private RelationServerSessionMng() {
    }

    @Override
    public void updateLogic(long dt) {

    }

    @Override
    public AbstractSession createSession(Channel channel) {
        RelationServerSession session = new RelationServerSession(channel);
        session.setResponseMng(RelationServerResponseMng.getInstance());
        return session;
    }

    public void broadcastByArea(MessageLite msg, int areaId) {
        for (AbstractSession session : getRegisterSessions().values()) {
            if (areaId == ((ServerTag) session.getTag()).getAreaId()) {
                session.sendMessage(msg);
            }
        }
    }
}

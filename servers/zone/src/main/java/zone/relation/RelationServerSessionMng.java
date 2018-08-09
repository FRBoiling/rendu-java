package zone.relation;

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
    public void updateLogic(long dt) {

    }

    @Override
    public AbstractSession createSession(Channel channel) {
        RelationServerSession session = new RelationServerSession(channel);
        session.setResponseMng(RelationServerResponseMng.getInstance());
        return session;
    }
}

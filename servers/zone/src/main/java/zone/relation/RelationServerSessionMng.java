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

    private static volatile RelationServerSessionMng INSTANCE = new RelationServerSessionMng();
    private RelationServerSessionMng(){
    }

    public static RelationServerSessionMng getInstance() {
        if (INSTANCE == null) {
            synchronized (RelationServerSessionMng.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RelationServerSessionMng();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public AbstractSession createSession(Channel channel) {
        return new RelationServerSession(channel);
    }
}

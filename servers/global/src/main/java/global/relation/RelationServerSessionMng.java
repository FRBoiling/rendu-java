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

    public void broadcastByGroup(MessageLite msg, int groupId) {
        for (AbstractSession session: getRegisterSessions().values()) {
            if ( groupId == ((ServerTag)session.getTag()).getGroupId()){
                session.sendMessage(msg);
            }
        }
    }
}

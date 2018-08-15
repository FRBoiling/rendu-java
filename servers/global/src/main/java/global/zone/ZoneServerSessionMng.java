package global.zone;

import com.google.protobuf.MessageLite;
import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import core.base.common.ISessionTag;
import core.base.model.ServerTag;
import io.netty.channel.Channel;

import java.util.Map;

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
    public AbstractSession createSession(Channel channel) {
        ZoneServerSession session = new ZoneServerSession(channel);
        session.setResponseMng(ZoneServerResponseMng.getInstance());
        return session;
    }

    /**
     * 按组发
     * @param msg 消息
     * @param areaId 服务端组号
     */
    public void broadcastByArea(MessageLite msg, int areaId) {
        for (Map.Entry<ISessionTag, AbstractSession> entry : getRegisterSessions().entrySet()) {
            if (((ServerTag)entry.getKey()).getAreaId() == areaId) {
                entry.getValue().sendMessage(msg);
            }
        }
    }
}

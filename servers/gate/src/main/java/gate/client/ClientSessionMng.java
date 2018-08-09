package gate.client;

import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import io.netty.channel.Channel;
import io.netty.util.internal.ConcurrentSet;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:09
 */
@Slf4j
public class ClientSessionMng extends AbstractSessionManager {

    private static ClientSessionMng INSTANCE = new ClientSessionMng();

    public static ClientSessionMng getInstance() {
        return INSTANCE;
    }

    private ClientSessionMng() {
    }

    @Override
    public AbstractSession createSession(Channel channel) {
        ClientSession session = new ClientSession(channel);
        session.setResponseMng(ClientResponseMng.getInstance());
        return session;
    }

    @Override
    public void updateLogic(long dt) {
        ClientLoginMng.getInstance().update(dt);
    }
}


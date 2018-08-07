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

    private static volatile ClientSessionMng INSTANCE = new ClientSessionMng();

    private ClientSessionMng() {
    }

    public static ClientSessionMng getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractSession createSession(Channel channel) {
        return new ClientSession(channel);
    }

    @Override
    public void updateLogic(long dt) {
        ClientLoginMng.getInstance().update(dt);
    }
}


package gate.client;

import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:09
 */

public class ClientSessionMng extends AbstractSessionManager {

    private static volatile ClientSessionMng INSTANCE = new ClientSessionMng();
    private ClientSessionMng(){
    }

    public static ClientSessionMng getInstance() {
        return INSTANCE;
    }

    @Override
    public AbstractSession createSession(Channel channel) {
        return new ClientSession(channel);
    }

    @Override
    public void update(long dt){
        super.update(dt);
        ClientLoginMng.getInstance().update(dt);
    }



}

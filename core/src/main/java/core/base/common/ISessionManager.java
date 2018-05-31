package core.base.common;

import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 15:37
 */

public interface ISessionManager {
    void register(AbstractSession session);
    void unregister(AbstractSession session);
    AbstractSession createSession(Channel channel);
}

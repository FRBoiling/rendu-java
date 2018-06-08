package core.base.common;

import core.base.concurrent.queue.AbstractHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 15:37
 */
@Slf4j
public abstract class AbstractSessionManager {
    ConcurrentHashMap<String,AbstractSession> sessions = new ConcurrentHashMap<>(10);
    public void register(AbstractSession session)
    {
        if ( session==null)
        {
            log.error("register session fail: session = null");
            return;
        }
        if ( sessions.containsKey(session.getKey())){
            log.error("register session fail: repeated session {}",session.getKey());
        }else{
            session.setRegistered(true);
            sessions.put(session.getKey(),session);
        }
    }
    public void unregister(AbstractSession session)
    {
        if ( session==null)
        {
            log.error("unregister session fail: session = null");
            return;
        }
        if ( sessions.containsKey(session.getKey())){
            session.setRegistered(false);
            sessions.remove(session.getKey());
        }else{
            log.error("unregister session fail: count found session {}",session.getKey());
        }
    }

    public abstract AbstractSession createSession(Channel channel);
}

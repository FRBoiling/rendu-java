package core.base.common;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

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
    ConcurrentHashMap<String, AbstractSession> sessions = new ConcurrentHashMap<>(10);

    public boolean register(AbstractSession session) {
        if (session == null) {
            log.error("register session fail: session = null");
            return false;
        }
        AbstractSession temSession = sessions.get(session.getKey());
        if (temSession != null) {
            if (temSession.isOffline()) {
                session.setRegistered(true);
                session.setOffline(false);
                sessions.put(session.getKey(), session);
            } else {
                log.error("register session fail: repeated session {}", session.getKey());
                return false;
            }
        } else {
            session.setRegistered(true);
            session.setOffline(false);
            sessions.put(session.getKey(), session);
        }
        log.info("register success: {} ", session.getKey());
        return true;
    }

    public boolean unregister(AbstractSession session) {
        if (session == null) {
            log.error("unregister session fail: session = null");
            return false;
        }
        if (sessions.containsKey(session.getKey())) {
            session.setRegistered(false);
            session.setOffline(true);
            sessions.remove(session.getKey());
        } else {
            log.error("unregister session fail: count found session {}", session.getKey());
            return false;
        }
        return true;
    }
    
    public void update(){
        for (AbstractSession session: sessions.values()) {
            try {
                session.update();
            }catch (Exception e){
                log.error(e.toString());
            }
        }
    }

    public abstract AbstractSession createSession(Channel channel);
}

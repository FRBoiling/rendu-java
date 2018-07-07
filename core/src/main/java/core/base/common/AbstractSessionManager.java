package core.base.common;

import io.netty.channel.Channel;
import io.netty.util.internal.ConcurrentSet;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
    ConcurrentSet<AbstractSession> allSession = new ConcurrentSet<AbstractSession>();
    private ArrayList<AbstractSession> removeSession = new ArrayList<AbstractSession>() ;

    ConcurrentHashMap<String, AbstractSession> registerSessions = new ConcurrentHashMap<>(10);

    public boolean register(AbstractSession session) {
        if (session == null) {
            log.error("register session fail: session = null");
            return false;
        }

        if (!allSession.contains(session)){
            log.error("register session fail: session not exist");
            return false;
        }

        AbstractSession temSession = registerSessions.get(session.getKey());
        if (temSession != null) {
            if (temSession.isOffline()) {
                //TODO:Boil 离线
                session.setRegistered(true);
                session.setOffline(false);
                registerSessions.put(session.getKey(), session);
                log.info("register session {} offline ", session.getKey());
            } else {
                //TODO:Boil 重复注册
                log.error("register session fail: repeated session {}", session.getKey());
                return false;
            }
        } else {
            session.setRegistered(true);
            session.setOffline(false);
            registerSessions.put(session.getKey(), session);
            log.info("register success: {} ", session.getKey());
        }

        return true;
    }

    public boolean unregister(AbstractSession session) {
        if (session == null) {
            log.error("unregister session fail: session = null");
            return false;
        }
        if (!allSession.contains(session)){
            log.error("unregister session fail: session not exist");
            return false;
        }else {
            allSession.remove(session);
        }

        if (registerSessions.containsKey(session.getKey())) {
            session.setRegistered(false);
            session.setOffline(true);
            registerSessions.remove(session.getKey());
        } else {
            log.error("unregister session fail: can't found session {}", session.getKey());
            return false;
        }
        return true;
    }
    
    public void update(){
        for (AbstractSession session: allSession) {
            try {
                session.update();
            }catch (Exception e){
                log.error(e.toString());
            }
        }
        if (removeSession.size()>0){
            for (AbstractSession session:removeSession) {
                allSession.remove(session);
            }
            removeSession.clear();
        }
    }

    public abstract AbstractSession createSession(Channel channel);

    public boolean addSession(AbstractSession session) {
        if (session == null) {
            log.error("add session fail: session = null");
            return false;
        }
        if (allSession.contains(session)) {
          //  log.error("add session fail: repeated session {}", session.getKey());
            return false;
        } else {
            allSession.add(session);
        }
        log.info("add session {} success! ", session.getIP());
        return true;
    }

}

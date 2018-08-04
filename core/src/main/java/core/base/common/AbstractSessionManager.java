package core.base.common;

import com.google.protobuf.MessageLite;
import constant.RegisterResult;
import core.base.model.ServerTag;
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
    ConcurrentSet<AbstractSession> allSession = new ConcurrentSet<>();
    private ArrayList<AbstractSession> removeSession = new ArrayList<>() ;

    ConcurrentHashMap<ISessionTag, AbstractSession> registerSessions = new ConcurrentHashMap<>(10);

    public RegisterResult register(AbstractSession session) {
        if (session == null) {
            log.error("register session fail: session = null");
            return RegisterResult.FAIL;
        }

        if (!allSession.contains(session)){
            log.error("register session fail: session not exist");
            return RegisterResult.FAIL;
        }

        AbstractSession temSession = registerSessions.get(session.getTag());
        if (temSession != null) {
            if (temSession.isOffline()) {
                //离线
                //正常连接注册（登入）
                session.setRegistered(true);
                session.setOffline(false);
                registerSessions.put(session.getTag(), session);
                log.info("register session {} offline ", session.getTag());
            } else {
                //在线
                // TODO:Boil 重复注册（重复登入，如，客户端登入 俗称顶号）
                log.warn("register session fail: repeated session {}", session.getTag());
                return RegisterResult.REPEATED_REGISTER;
            }
        } else {
            session.setRegistered(true);
            session.setOffline(false);
            registerSessions.put(session.getTag(), session);
            log.info("register success: {} ", session.getTag());
        }

        return RegisterResult.SUCCESS;
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

        if (registerSessions.containsKey(session.getTag()) && session.isRegistered()) {
            session.setRegistered(false);
            session.setOffline(true);
            registerSessions.remove(session.getTag());
        } else {
            log.error("unregister session fail: can't found session {}", session.getTag());
            return false;
        }
        return true;
    }
    
    public void update(long dt){
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

    public ConcurrentHashMap<ISessionTag, AbstractSession>  getRegisterSessions() {
        return registerSessions;
    }

    public AbstractSession getRegisterSession(ISessionTag tag){
        return registerSessions.get(tag);
    }

    public void broadcastAll(MessageLite msg){
        for (AbstractSession session: registerSessions.values()) {
            session.sendMessage(msg);
        }
    }

    public void broadcastAllExceptServer(MessageLite msg,ServerTag tag){
        for (AbstractSession session: registerSessions.values()) {
            if (!session.getTag().equals(tag)){
                session.sendMessage(msg);
            }
        }
    }

    public void broadcastByGroup(MessageLite msg, int groupId) {
        for (AbstractSession session: getRegisterSessions().values()) {
            if ( groupId == ((ServerTag)session.getTag()).getGroupId()){
                session.sendMessage(msg);
            }
        }
    }

    public void broadcastByGroupExceptServer(MessageLite msg, int groupId,ServerTag tag) {
        for (AbstractSession session: getRegisterSessions().values()) {
            if ( groupId == ((ServerTag)session.getTag()).getGroupId()){
                if (!session.getTag().equals(tag)) {
                    session.sendMessage(msg);
                }
            }
        }
    }


    public void send2Session(ISessionTag sessionTag,MessageLite msg){
        AbstractSession session = getRegisterSession(sessionTag);
        if (session!=null )
        {
            session.sendMessage(msg);
        }
    }
}

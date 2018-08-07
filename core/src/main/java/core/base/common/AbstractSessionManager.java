package core.base.common;

import com.google.protobuf.MessageLite;
import constant.RegisterResult;
import core.base.model.ServerTag;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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

    private ConcurrentHashMap<Integer,AbstractSession> connectingHashMap = new ConcurrentHashMap<>(128);

    private Set<AbstractSession> allSession = new HashSet<>();

    private ArrayList<AbstractSession> removeSessions = new ArrayList<>() ;

    private HashMap<ISessionTag, AbstractSession> registerSessions = new HashMap<>(16);

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
            if (temSession.getChannel() == session.getChannel()){
                //相同会话 返回已经注册
                session.setRegistered(true);
                session.setOffline(false);
                return RegisterResult.ALREADY_REGISTER;
            }
            else {
                //不同会话
                if (temSession.isOffline()) {
                    //离线
                    //1 取消原来注册的会话
                    unregister(temSession);
                    //2 连接注册新会话
                    session.setRegistered(true);
                    session.setOffline(false);
                    registerSessions.put(session.getTag(), session);
                    log.info("register session {} offline ", session.getTag());
                } else {
                    //在线
                    //会话重复注册
                    log.warn("register session fail: repeated session {}", session.getTag());
                    //TODO:BOIL 目前先不进行注册，只返回错误码
                    return RegisterResult.REPEATED_REGISTER;
                }
            }
        } else {
            session.setRegistered(true);
            session.setOffline(false);
            registerSessions.put(session.getTag(), session);
            log.info("register success: {} ", session.getTag());
        }

        return RegisterResult.SUCCESS;
    }

    private void unregister(AbstractSession session) {
        if (session == null) {
            log.error("unregister session fail: session = null");
            return;
        }
        if (!allSession.contains(session)){
            log.error("unregister session fail: session not exist");
            return;
        }else {
            allSession.remove(session);
        }

        if (registerSessions.containsKey(session.getTag()) && session.isRegistered()) {
            session.setRegistered(false);
            session.setOffline(true);
            registerSessions.remove(session.getTag());
        } else {
            log.error("unregister session fail: can't found session {}", session.getTag());
        }
    }
    
    public void update(long dt){
        addConnectingSession();
        if (removeSessions.size()>0){
            for (AbstractSession session: removeSessions) {
                 unregister(session);
            }
            removeSessions.clear();
        }
        updateLogic(dt);
        for (AbstractSession session: allSession) {
            try {
                session.update();
            }catch (Exception e){
                log.error(e.toString());
            }
        }
    }

    public abstract void updateLogic(long dt);

    public abstract AbstractSession createSession(Channel channel);

    public void putSession(AbstractSession session) {
        connectingHashMap.put(session.hashCode(), session);
    }

    private void addConnectingSession() {
        allSession.addAll(connectingHashMap.values());
        connectingHashMap.clear();
    }

//    private boolean addSession(AbstractSession session) {
//        if (session == null) {
//            log.error("add session fail: session = null");
//            return false;
//        }
//        if (allSession.contains(session)) {
//            log.error("add session fail: repeated session {}", session.getTag().toString());
//            return false;
//        } else {
//            allSession.add(session);
//        }
//        log.info("add session {} success! ", session.getIP());
//        return true;
//    }

    public void removeSession(AbstractSession session){
        if (session!=null){
            session.OnDisConnected();
            removeSessions.add(session);
        }
    }

    public HashMap<ISessionTag, AbstractSession>  getRegisterSessions() {
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

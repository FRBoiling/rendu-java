package core.base.common;

import com.google.protobuf.MessageLite;
import constant.RegisterResult;
import core.base.model.ServerTag;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static core.base.serviceframe.AbstractServiceFrame.now;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 15:37
 */
@Slf4j
public abstract class AbstractSessionManager {

    private ConcurrentHashMap<Integer, AbstractSession> connectingHashMap = new ConcurrentHashMap<>(128);

    private Set<AbstractSession> allSession = new HashSet<>();

    private ArrayList<AbstractSession> removeSessions = new ArrayList<>();

    private HashMap<ISessionTag, AbstractSession> registerSessions = new HashMap<>(16);
    private ArrayList<AbstractSession> removeCacheSessions = new ArrayList<>();

    public void init(){

    }

    public RegisterResult register(AbstractSession session) {
        if (session == null) {
            log.error("initRegister session fail: session = null");
            return RegisterResult.FAIL;
        }

        if (!allSession.contains(session)) {
            log.error("initRegister session fail: session not exist");
            return RegisterResult.FAIL;
        }

        AbstractSession temSession = registerSessions.get(session.getTag());
        if (temSession == null) {
            session.initRegister();
            registerSessions.put(session.getTag(), session);
            log.info("initRegister session success: {} ", session.getTag());
        } else {
            if (temSession.equals(session)) {
                //相同会话
                session.initRegister();
                log.warn("initRegister session fail:session {} got a same channel", session.getTag());
                return RegisterResult.ALREADY_REGISTER;
            } else {
                //不同会话
                if (temSession.isOffline()) {
                    //离线
                    //1 取消原来注册的会话
                    unregister(temSession);
                    session.initRegister();
                    registerSessions.put(session.getTag(), session);
                    log.info("initRegister session success: old session offline {}", temSession.getTag());
                } else {
                    //在线
                    //同账号不同地点登录
                    //逻辑在上层处理，这里不做操作，只返回错误码
                    log.warn("initRegister session fail: repeated session {}", session.getTag());
                    return RegisterResult.REPEATED_REGISTER;
                }
            }
        }

        return RegisterResult.SUCCESS;
    }

    private void unregister(AbstractSession session) {
        if (session == null) {
            log.error("unregister session fail: session = null");
            return;
        }
        //remove from initRegister sessions
        if (registerSessions.containsKey(session.getTag()) && session.isRegistered()) {
            registerSessions.remove(session.getTag());
            session.unRegister(now);
        }
        else {
            log.error("unregister session fail: can't found initRegister session {}", session.getTag());
        }
        log.info("unregister session success:{}",session.getTag().toString());
    }

    public void update(long dt) {
        addConnectingSession();
        if (removeSessions.size() > 0) {
            for (AbstractSession session : removeSessions) {
                //remove from allSession
                if (allSession.contains(session)) {
                    session.onDisConnected();
                    allSession.remove(session);
                    log.info("update to remove session success: {}", session.getChannel().toString());
                } else {
                    log.error("update to remove session fail: session {} not exist", session.getTag().toString());
                }
            }
            removeSessions.clear();
        }
        updateLogic(dt);
        for (AbstractSession session : allSession) {
            try {
                session.update();
            } catch (Exception e) {
                log.error("session mng update error:{}", e.toString());
            }
        }

        registerSessionsUpdate(now);
    }

    private void registerSessionsUpdate(long dt) {
        for (Map.Entry<ISessionTag, AbstractSession> session : registerSessions.entrySet()) {
            ISessionTag t = session.getKey();
            AbstractSession s = session.getValue();
            if (s.isOffline()) {
                if (s.isRegistered()) {
                    if (s.checkClearOfflineCache(dt)) {
                        removeCacheSessions.add(s);
                    }
                } else {
                    log.error("{} got an error initRegister info", t.toString());
                    s.setRegistered(true);
                }
            }
        }
        for (AbstractSession session : removeCacheSessions) {
            unregister(session);
        }
        removeCacheSessions.clear();
    }

    public abstract void updateLogic(long dt);

    public abstract AbstractSession createSession(Channel channel);

    /**
     * 添加session
     */
    public void putSession(AbstractSession session) {
        connectingHashMap.put(session.hashCode(), session); //线程安全的connectingHashMap先接过来
    }

    /**
     * 从connectingHashMap添加到主线程循环中的allSession
     */
    private void addConnectingSession() {
        if (connectingHashMap.size() > 0) {
//            log.debug("addConnectingSession count {}",connectingHashMap.size());
            allSession.addAll(connectingHashMap.values());
            connectingHashMap.clear();
        }
    }

    /**
     * 移除session
     *
     * @param session 会话
     */
    public void removeSession(AbstractSession session) {
        if (session != null) {
            session.setOffline(now);
            removeSessions.add(session);
        }
    }

    public HashMap<ISessionTag, AbstractSession> getRegisterSessions() {
        return registerSessions;
    }

    public AbstractSession getRegisterSession(ISessionTag tag) {
        return registerSessions.get(tag);
    }

    public void broadcastAll(MessageLite msg) {
        for (Map.Entry<ISessionTag, AbstractSession> entry : registerSessions.entrySet()) {
            entry.getValue().sendMessage(msg);
        }
    }

    /**
     * 除tag以外
     *
     * @param msg 消息
     * @param tag 排除的tag
     */
    public void broadcastAllExceptServer(MessageLite msg, ServerTag tag) {
        for (Map.Entry<ISessionTag, AbstractSession> entry : registerSessions.entrySet()) {
            if (!entry.getKey().equals(tag)) {
                entry.getValue().sendMessage(msg);
            }
        }
    }

    /**
     * 按组发
     *
     * @param msg     消息
     * @param areaId 服务端组号
     */
    public void broadcastByArea(MessageLite msg, int areaId) {
        for (Map.Entry<ISessionTag, AbstractSession> entry : registerSessions.entrySet()) {
            if (((ServerTag) entry.getKey()).getAreaId() == areaId) {
                entry.getValue().sendMessage(msg);
            }
        }
    }

    public void broadcastByAreaExceptServer(MessageLite msg, int areaId, ServerTag tag) {

        for (Map.Entry<ISessionTag, AbstractSession> entry : registerSessions.entrySet()) {
            ServerTag sessionTag = (ServerTag) entry.getKey();
            if (sessionTag.getAreaId() == areaId) {
                if (!sessionTag.equals(tag)) {
                    entry.getValue().sendMessage(msg);
                }
            }
        }
    }

    public void send2Session(ISessionTag sessionTag, MessageLite msg) {
        AbstractSession session = getRegisterSession(sessionTag);
        if (session != null) {
            session.sendMessage(msg);
        }
    }
}

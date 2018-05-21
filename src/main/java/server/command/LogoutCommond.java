package server.command;


import core.base.common.AttributeUtil;
import core.base.concurrent.command.AbstractCommand;
import lombok.extern.slf4j.Slf4j;
import server.Session;
import server.SessionKey;
import server.system.user.UserManager;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 14:19
 */
@Slf4j
public class LogoutCommond extends AbstractCommand {

    private Session session;
    public LogoutCommond(Session session) {
        this.session = session;
    }

    @Override
    public void doAction() {

        Boolean logoutHandled =  AttributeUtil.get(session.getChannel(), SessionKey.LOGOUT_HANDLED);
        if(Boolean.TRUE.equals(logoutHandled)) {
            log.error("网络连接断开的时候玩家已经处理过下线事件[顶号]->{}", session.getUser().toString());
            return;
        }
        AttributeUtil.set(session.getChannel(), SessionKey.LOGOUT_HANDLED, true);
        //登出
        UserManager.getInstance().logout(session);
        log.error("网络连接断开，处理玩家下线逻辑->{}", session.getUser().toString());
    }

    @Override
    public Object getParam() {
        return null;
    }

    @Override
    public void setParam(Object param) {

    }
}

package com.wanart.server.system.user;

import com.wanart.server.Session;
import com.wanart.server.basedata.User;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 15:00
 */
@Slf4j
public class UserManager {
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager() {
    }

    /**
     * 登录游戏
     *
     * @param session session
     */
    public void login(Session session, String loginName) {
        if (loginName.isEmpty()) {
            return;
        }
//        User user = DataCenter.getUser(loginName);
//        if (user == null) {
//            // 新建用户
//            user = createUser(loginName);
//            if (user == null) {
//                log.error("用户创建失败:{},{},{}", loginName);
//                session.close();
//                return;
//            }
//        }
//        // 注册账户
//        session.setUser(user);
//        // 注册session
//        SessionManager.getInstance().register(session);
//
//        LoginResponse.Builder builder = LoginResponse.newBuilder();
//        LoginResponse loginResponse = builder.setUserId(user.getId()).build();
//        MessageUtil.sendMsg(loginResponse, user.getId());
//
//        EventUtil.fireEvent(EventType.LOGIN, user);
    }


    /**
     * 创建角色
     *
     * @param loginName loginName
     * @return User
     */
    private User createUser(String loginName) {
        User user = new User();
//        long id = IDUtil.getId();
//        user.setId(id);
//        user.setLoginName(loginName);
//        user.setServerId(1);
//        user.setGmLevel(1);
//        user.setPlatformId(1);
//        user.setRegisterTime(TimeUtil.getNowOfSeconds());
//        DataCenter.insertData(user, true);
//        DataCenter.registerUser(user);
        return user;
    }


    /**
     * 退出
     */
    public void logout(Session session) {
//        DataCenter.updateData(session.getUser().getId(), DataType.USER, true);
//        EventUtil.fireEvent(EventType.LOGOUT, session.getUser());
    }
}

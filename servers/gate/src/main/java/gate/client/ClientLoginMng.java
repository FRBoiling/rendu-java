package gate.client;

import constant.CONSTANT;
import constant.ErrorCode;
import constant.OnlineLoadState;
import gamedb.dao.account.SelectAccountDBOperator;
import gamedb.dao.accountRoleList.SelectAccountRoleDBOperator;
import gamedb.dao.otherDao.CreateAccountDBOperator;
import gamedb.pojo.accountRoleList.AccountRolePOJO;
import gamedb.pojo.account.AccountPOJO;
import gate.Context;
import gate.GateService;
import lombok.extern.slf4j.Slf4j;
import protocol.client.Client.*;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-03
 * Time: 19:38
 **/
@Slf4j
public class ClientLoginMng {
    private static volatile ClientLoginMng INSTANCE = new ClientLoginMng();

    private ClientLoginMng() {
    }

    public static ClientLoginMng getInstance() {
        return INSTANCE;
    }

    private Queue<ClientSession> loginWaitQueue = new ArrayDeque<>();

    private OnlineLoadState onlineLoadState = OnlineLoadState.NORMAL;

    private long loginWaitQueueTime = CONSTANT.LOGIN_QUEUE_PERIOD;
    private long loginDeltaTime = 0;
    private long nextNotifyWaitingTime = 0;

    public void login(ClientSession session) {
        loginWaitQueue.add(session);
    }

    void update(double dt) {
        switch (onlineLoadState) {
            case NORMAL:
                // 放人
                while (loginWaitQueue.size() > 0) {
                    if (pollLoginWaitQueue()) break;
                }
                break;
            case WAIT:
                if (loginDeltaTime < loginWaitQueueTime) {
                    loginDeltaTime += dt;
                } else {
                    // 放人
                    while (loginWaitQueue.size() > 0) {
                        if (pollLoginWaitQueue()) break;
                    }
                    loginDeltaTime = 0;
                }
                NotifyWaitingClients();
                break;
            case FULL:
                NotifyWaitingClients();
                break;
        }
    }

    private boolean pollLoginWaitQueue() {
        ClientSession clientSession = loginWaitQueue.poll();
        if (clientSession != null) {
            if (clientSession.isRegistered()) {
                clientSession.login();
                return true;
            }
        }
        return false;
    }

    private void NotifyWaitingClients() {
        if (Context.now > nextNotifyWaitingTime && loginWaitQueue.size() > 0) {
            // 整理排队信息 清除在排队时间内离开的client
            Queue<ClientSession> tmpQueue = new ArrayDeque<>();
            while (loginWaitQueue.size() > 0) {
                ClientSession clientSession = loginWaitQueue.poll();
                if (clientSession != null) {
                    if (clientSession.isRegistered()) {
                        tmpQueue.offer(clientSession);
                    }
                }
            }
            loginWaitQueue = tmpQueue;
            int index = 0;
            for (ClientSession session : tmpQueue) {
//                sendWaitingTime((++index) * acceptor.BarrackServer.GateCount);
                sendWaitingTime(session, 1111);
            }
            // 通知排队情况
            nextNotifyWaitingTime = Context.now + CONSTANT.NOTIFY_WAITING_TIME_PERIOD;
        }
    }

    private void sendWaitingTime(ClientSession session, int index) {
        //TODO:BOIL 发送等待通知
//        MSG_GC_LOGIN_WAIT_QUEUE msg = new MSG_GC_LOGIN_WAIT_QUEUE();
//        notify.index = index;
//        notify.waitingTime = index * CONSTANT.LOGIN_QUEUE_PERIOD;
//        session.sendMessage(msg);
    }

    void loadAccount(ClientSession clientSession) {
        AccountPOJO pojo = clientSession.getAccountPOJO();

        MSG_GC_USER_LOGIN.Builder response;
        response = MSG_GC_USER_LOGIN.newBuilder();
        response.setUsername(pojo.getUsername());
        response.setAreaId(Context.tag.getAreaId());
        response.setResult(ErrorCode.SUCCESS.ordinal());
        response.setToken("");

        SelectAccountDBOperator queryLogin = new SelectAccountDBOperator(pojo);
        GateService.context.db.Call(queryLogin, (tempOperator) -> {

            SelectAccountDBOperator op = (SelectAccountDBOperator) tempOperator;
            if (op.getResult() == 1) {
//                //账号存在
//                //如果registerId更改就去修改一下表
//                if (!pojo.getRegisterId().equals(op.account.getRegisterId())) {
//                    AccountPOJO temp = op.account;
//                    //更新registerId
//                    UpdateAccountRegisterIdDBOperator operator=new UpdateAccountRegisterIdDBOperator(op.account.getUsername(),op.account.getChannelName(),pojo.getRegisterId())
//                    GateService.context.db.Call(operator);
//                }
                //角色信息获取
                getRoleList(clientSession, response);
            } else if (op.getResult() == 0) {
                //创建账号
                createAccount(clientSession, response);
            }
        });
    }

    private void createAccount(ClientSession clientSession, MSG_GC_USER_LOGIN.Builder response) {
        GateService.context.db.Call(new CreateAccountDBOperator(clientSession.getAccountPOJO()), (ret) -> {
            CreateAccountDBOperator op = (CreateAccountDBOperator) ret;
            if (op.getResult() == 1) {
                response.setResult(ErrorCode.SUCCESS.getValue());
                clientSession.sendMessage(response.build());
                //TODO:BOIL 埋点
            } else {
                //TODO:BOIL 加入数据库失败
                log.error("createAccount got an db error!!!!");
                response.setResult(ErrorCode.NotCreating.getValue());
                clientSession.sendMessage(response.build());
            }
        });
    }

    private void getRoleList(ClientSession clientSession, MSG_GC_USER_LOGIN.Builder response) {
        //TODO:BOIL 我在想是不是把这块用redis实现
        GateService.context.db.Call(new SelectAccountRoleDBOperator(clientSession.getAccountPOJO()), (ret) -> {
            SelectAccountRoleDBOperator op = (SelectAccountRoleDBOperator) ret;
            if (op.getResult() == 1) {
                for (Map.Entry<Integer,AccountRolePOJO>  pojo: op.getAccountRolePOJOs().entrySet()){
                    AccountRolePOJO p = pojo.getValue();
                    ROLE_LOGIN_INFO.Builder info = ROLE_LOGIN_INFO.newBuilder();
                    info.setUid(p.getUid());
                    info.setName(p.getRoleName());
                    info.setAreaId(p.getAreaId());
                    info.setOriginalAreaId(p.getAreaId());
                    //TODO:添加其他必要信息
//                    //如合区后的原始登录入口originalAreaId
//                    info.setOriginalAreaId( );
                    response.addRoleList(info);
                }
                response.setResult(ErrorCode.SUCCESS.getValue());
                clientSession.sendMessage(response.build());
            }
        });
    }

    public void sendLoginResponse(ClientSession session, MSG_GC_USER_LOGIN.Builder response) {
        log.debug("account {} login response error code {}", response.getUsername(), response.getResult());
        if (response.getResult() != ErrorCode.SUCCESS.ordinal()) {
            if (response.getResult() != ErrorCode.BadToken.ordinal()) {
                //生成新的token
                String strToken = UUID.randomUUID().toString().replace("-", "");
                response.setToken(strToken);
                session.sendMessage(response.build());
            }
        }
    }
}

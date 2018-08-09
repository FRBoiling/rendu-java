package gate.client;

import constant.CONSTANT;
import constant.ErrorCode;
import constant.OnlineLoadState;
import gamedb.DBOperateType;
import gamedb.dao.account.CreateAccountDBOperator;
import gamedb.dao.account.SelectAccountDBOperator;
import gamedb.pojo.account.AccountPOJO;
import gate.Context;
import gate.GateService;
import lombok.extern.slf4j.Slf4j;
import protocol.client.Client.*;

import java.util.ArrayDeque;
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
        if (clientSession!=null){
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
        AccountPOJO pojo = new AccountPOJO();
        pojo.setAccountName(clientSession.getAccountObject().getAccountName());
        pojo.setChannelName(clientSession.getAccountObject().getChannelName());

        MSG_GC_USER_LOGIN.Builder response;
        response = MSG_GC_USER_LOGIN.newBuilder();
        response.setAccountName(pojo.getAccountName());
        response.setResult(ErrorCode.SUCCESS.ordinal());
        response.setToken("");

        SelectAccountDBOperator queryLogin = new SelectAccountDBOperator(pojo);
        GateService.context.db.Call(queryLogin, "account", DBOperateType.Read, (tempOperator) -> {

            SelectAccountDBOperator op = (SelectAccountDBOperator) tempOperator;
            if (op.getResult() == 1) {
                //// 账号存在
                //如果registerId更改就去修改一下表
                if (clientSession.getAccountObject().getRegisterId().equals(op.account.getRegisterId())) {
                    AccountPOJO temp = op.account;
                    //更新registerId
                    //GateService.context.db.Call(new QueryUpdateAccountRegisterId(AccountName, ChannelName, RegisterId), "account", DBOperateType.Write);
                    response.setResult(ErrorCode.SUCCESS.getValue());
                    clientSession.sendMessage(response.build());
                }
                //TODO 去数据库取角色数据

            } else if (op.getResult() == 0) {
                AccountPOJO accountPOJO = new AccountPOJO();
                accountPOJO.setAccountName(clientSession.getAccountObject().getAccountName());
                accountPOJO.setChannelName(clientSession.getAccountObject().getChannelName());
                accountPOJO.setDeviceId(clientSession.getAccountObject().getDeviceId());
                accountPOJO.setRegisterId(clientSession.getAccountObject().getRegisterId());
                GateService.context.db.Call(new CreateAccountDBOperator(accountPOJO), "account", DBOperateType.Write, (tempOperator2) -> {
                    //埋点
                    response.setResult(ErrorCode.SUCCESS.getValue());
                    clientSession.sendMessage(response.build());
                });
            }
        });
    }

    public void sendLoginResponse(ClientSession session,MSG_GC_USER_LOGIN.Builder response){
        log.debug("account {} login response error code {}",response.getAccountName(),response.getResult());
        if ( response.getResult() != ErrorCode.SUCCESS.ordinal()){
            if (response.getResult()!=ErrorCode.BadToken.ordinal()){
                //生成新的token
                String strToken = UUID.randomUUID().toString().replace("-","");
                response.setToken(strToken);
                session.sendMessage(response.build());
            }
        }
    }
}

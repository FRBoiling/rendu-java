package gate.client.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.RegisterResult;
import core.base.common.AbstractSession;
import core.base.model.ClientTag;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import dataObject.AccountObject;
import gate.client.AuthorizationMng;
import gate.client.ClientLoginMng;
import gate.client.ClientSession;
import gate.client.ClientSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.client.c2g.C2G.*;

@Slf4j
public class ResponseUserLogin implements IResponseHandler {
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ClientSession clientSession = (ClientSession) session;

        MSG_CG_USER_LOGIN msg = MSG_CG_USER_LOGIN.parseFrom(packet.getMsg());
        String accountName = msg.getAccountName();
        int token = msg.getToken();

        log.info("account {} request to login ", accountName);

        //token检查
        if (!AuthorizationMng.getInstance().checkToken(accountName, token)) {
            log.warn("account {} got en authorize fail:wrong token {}", accountName, token);
            return;
        }

        ((ClientTag) clientSession.getTag()).setTag(accountName, token);
        //TODO:BOIL token 什么时候失效？？

        //TODO:BOIL 白名单，版本等判断

        //登入gate
        RegisterResult registerResult = ClientSessionMng.getInstance().register(session);
        switch (registerResult) {
            case SUCCESS:
                //TODO:BOIL 正常登陆流程
                break;
            case REPEATED_REGISTER:
                //TODO:BOIL 重复登入顶号流程
                break;
            case FAIL:
                //TODO:BOIL 登陆失败流程
                break;
        }

        AccountObject accountObject = new AccountObject(accountName);

        accountObject.setDeviceId(msg.getDeviceId());

        if (msg.getRegisterId() != null) {
            accountObject.setRegisterId(msg.getRegisterId());
        }

        if (msg.getChannelName() != null && !msg.getChannelName().isEmpty()) {
            accountObject.setChannelName(msg.getChannelName());
        }

        clientSession.setAccountObject(accountObject);

        //所有验证通过，登录到游戏流程
        ClientLoginMng.getInstance().login(clientSession);
    }
}

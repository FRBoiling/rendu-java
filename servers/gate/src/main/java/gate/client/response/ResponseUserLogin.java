package gate.client.response;

import com.google.protobuf.InvalidProtocolBufferException;
import constant.ErrorCode;
import constant.RegisterResult;
import core.base.common.AbstractSession;
import core.base.model.ClientTag;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import gamedb.pojo.account.AccountPOJO;
import gate.GateService;
import gate.client.AuthorizationMng;
import gate.client.ClientLoginMng;
import gate.client.ClientSession;
import gate.client.ClientSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.client.Client.*;

@Slf4j
public class ResponseUserLogin implements IResponseHandler {
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ClientSession clientSession = (ClientSession) session;

        MSG_CG_USER_LOGIN msg = MSG_CG_USER_LOGIN.parseFrom(packet.getMsg());
        String username = msg.getUsername();
//        String channelName = msg.getChannelName();
        String channelName ="default_channel";

        MSG_GC_USER_LOGIN.Builder response = MSG_GC_USER_LOGIN.newBuilder();
        response.setUsername(username);

        if (!GateService.context.isOpened()) {
            response.setResult(ErrorCode.ServerNotOpen.ordinal());
            ClientLoginMng.getInstance().sendLoginResponse(clientSession, response);
            return;
        }

        log.info("account {} request to login ", username);
        //到这里的账号应该是合法的,不去验证账号
        //token检查
        if (!AuthorizationMng.getInstance().checkToken(username, channelName)) {
            log.warn("account {} got en authorize fail:wrong token {}", username, channelName);
            return;
        }

        ((ClientTag) clientSession.getTag()).setTag(username, channelName);
        //TODO:BOIL token 什么时候失效？？

        //TODO:BOIL 白名单，版本等判断

        //登入gate
        RegisterResult registerResult = ClientSessionMng.getInstance().register(session);
        switch (registerResult) {
            case ALREADY_REGISTER:
                //已经注册了
                return;
            case FAIL:
                response.setResult(ErrorCode.FAIL.ordinal());
                //注册失败
                session.sendMessage(response.build());
                return;
            case REPEATED_REGISTER:
                //重复登入顶号流程
                response.setResult(ErrorCode.FAIL.ordinal());
                //TODO:BOIL 通知当前已经登入的账号
                log.info("account {} repeated login :repeat channel {} (cur channel {})",
                        username, ClientSessionMng.getInstance().getRegisterSession(clientSession.getTag()).getChannel().toString(), clientSession.getChannel().toString());

                AbstractSession oldSession = ClientSessionMng.getInstance().getRegisterSession(session.getTag());
                oldSession.sendMessage(response.build());
                session.sendMessage(response.build());
                return;
            case SUCCESS:
                response.setResult(ErrorCode.SUCCESS.ordinal());
                //正常登陆流程
                AccountPOJO accountPOJO = new AccountPOJO();
                accountPOJO.setUsername(msg.getUsername());
                accountPOJO.setChannelName(msg.getChannelName());
                accountPOJO.setPassword(msg.getPassword());
                accountPOJO.setDeviceId(msg.getDeviceId());

//                accountPOJO.setRegisterId(msg.getRegisterId());
//                accountPOJO.setChannelName(msg.getChannelName());

                clientSession.setAccountPOJO(accountPOJO);
                //所有验证通过，登录到游戏流程
                ClientLoginMng.getInstance().login(clientSession);
                break;
        }

    }
}

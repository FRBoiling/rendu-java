package com.wanart.core.network;

import com.wanart.core.base.common.AttributeUtil;
import com.wanart.server.Session;
import com.wanart.server.SessionKey;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 13:58
 */
@Slf4j
public class NetworkListener implements INetworkEventListener {
    @Override
    public void onConnected(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        Session session = AttributeUtil.get(channel, SessionKey.SESSION);
        if (session == null) {
            session = new Session(channel);
            AttributeUtil.set(channel, SessionKey.SESSION, session);
            log.info("建立新的连接：" + channel.toString());
        } else {
            log.error("新连接建立时已存在Session，注意排查原因" + channel.toString());
        }
    }

    @Override
    public void onDisconnected(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        Session session = AttributeUtil.get(channel, SessionKey.SESSION);
        closeSession(session);
    }

    @Override
    public void onExceptionOccur(ChannelHandlerContext ctx, Throwable cause) {

    }

    public static void closeSession(Session session) {
        log.error("closeSession ");
        if (session == null || session.getUser() == null) {
            //下线
            log.error("服务端断开连接[没有找到服务端信息]");
            return;
        }

//        IProcessor processor = GameContext.getGameServer().getRouter().getProcessor(GameConst.QueueId.LOGIN_LOGOUT);
//        processor.process(new LogoutCommand(session));
    }
}

package server;

import core.base.common.AttributeUtil;
import core.network.INetworkEventListener;
import core.network.IProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import server.constant.GameConst;

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
            session.setChannel(channel);
            AttributeUtil.set(channel, SessionKey.SESSION, session);
            log.info("接收到新的连接：" + channel.toString());
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
            log.error("玩家断开连接[没有找到用户信息]");
            return;
        }

//        IProcessor processor = GameContext.getGameServer().getRouter().getProcessor(GameConst.QueueId.LOGIN_LOGOUT);
//        processor.process(new LogoutCommand(session));
    }
}

package core.network;

import core.base.common.AbstractSession;
import core.base.common.AbstractSessionManager;
import core.base.common.AttributeUtil;
import core.base.common.SessionKey;
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
    AbstractSessionManager sessionMng;
     public NetworkListener(AbstractSessionManager sessionMng)
     {
         this.sessionMng = sessionMng;
     }
    @Override
    public void onConnected(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        AbstractSession session = AttributeUtil.get(channel, SessionKey.SESSION);
        if (session == null) {
            try {
                session = sessionMng.createSession(channel);
                session.onConnected();
                log.debug("建立新的连接：{}" ,channel.toString());
                sessionMng.putSession(session);
                AttributeUtil.set(channel, SessionKey.SESSION, session);
            }catch (Exception e){
                log.error("onConnected error ：{} ",e.toString());
            }
        } else {
            log.error("新连接建立时已存在Session，注意排查原因 {}" , channel.toString());
        }
    }

    @Override
    public void onDisconnected(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.debug("断开连接：" + channel.toString());
        AbstractSession session = AttributeUtil.get(channel, SessionKey.SESSION);
        if (session == null)
        {
            //下线
            log.error("[没有找到有效会话]");
        }else {
            log.debug("remove session {}",session.getTag().toString());
            sessionMng.removeSession(session);
        }
    }

    @Override
    public void onExceptionOccur(ChannelHandlerContext ctx, Throwable cause) {

    }

//    public static void closeSession(AbstractSession session) {
//        log.error("closeSession ");
//        if (session == null ) {
//            //下线
//            log.error("[没有找到有效会话]");
//        }
//        else {
//            if (!session.isRegistered()) {
//                //下线
//                log.error("[没有找到会话注册信息]");
//            } else {
//
//            }
//        }
//
////        IProcessor processor = GameContext.getGameServer().getRouter().getProcessor(GameConst.QueueId.LOGIN_LOGOUT);
////        processor.process(new LogoutCommand(session));
//    }
}

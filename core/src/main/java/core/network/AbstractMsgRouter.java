package core.network;

import core.base.common.AbstractSession;
import core.base.common.AttributeUtil;
import core.base.common.SessionKey;
import core.base.concurrent.AbstractHandler;
import core.base.concurrent.IDriver;
import core.base.concurrent.QueueDriver;
import core.base.concurrent.queue.MessageDriver;
import core.network.codec.Packet;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-08
 * Time: 16:24
 */
@Slf4j
public abstract class AbstractMsgRouter implements INetworkConsumer {
    private IResponseHandlerManager responseHandlerManager;

    public AbstractMsgRouter(IResponseHandlerManager responseHandlerManager) {
        this.responseHandlerManager = responseHandlerManager;
    }
    @Override
    public void consume(Packet packet, Channel channel) {
        AbstractSession session = AttributeUtil.get(channel, SessionKey.SESSION);
        if (session == null) {
            return;
        }

        AbstractHandler handler = responseHandlerManager.getHandler(packet.getMsgId());
        if ( handler == null)
        {
            log.info("got an no registered msg:" + packet.getMsgId());
            return;
        }

        handler.setMessage(packet.getMsg());
        handler.setParam(session);
        session.getMessageDriver().addAction(handler);
    }
}

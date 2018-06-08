package core.network;

import core.base.common.AbstractSession;
import core.base.common.AttributeUtil;
import core.base.common.SessionKey;
import core.base.concurrent.queue.AbstractHandler;
import core.base.concurrent.queue.QueueDriver;
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
    private QueueDriver queueDriver;

    public AbstractMsgRouter(IResponseHandlerManager responseHandlerManager,QueueDriver queueDriver) {
        this.responseHandlerManager = responseHandlerManager;
        this.queueDriver = queueDriver;
    }
    @Override
    public void consume(Packet packet, Channel channel) {
        //TODO:boil 单逻辑线程的话，这里要做的是将消息加入到消息队列
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
        queueDriver.addAction(handler);
    }
}

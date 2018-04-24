package client;

import core.base.common.AttributeUtil;
import core.network.AbstractHandler;
import core.network.IMessageAndHandler;
import core.network.INetworkConsumer;
import core.network.IProcessor;
import core.network.codec.Packet;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import server.Session;
import server.SessionKey;
import core.constant.GameConst;
import server.processor.LogicProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-24
 * Time: 10:02
 */

@Slf4j
public class ClientMessageRouter implements INetworkConsumer {
    private Map<Integer, IProcessor> processors = new HashMap<>(10);

    private IMessageAndHandler msgPool;

    public ClientMessageRouter(IMessageAndHandler msgPool) {
        this.msgPool = msgPool;
    }

    public void initRouter() {
        //业务队列
        registerProcessor(GameConst.QueueId.LOGIC, new LogicProcessor());
    }

    private void registerProcessor(Integer queueId, IProcessor consumer) {
        processors.put(queueId, consumer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void consume(Packet packet, Channel channel) {
        Integer queueId = GameConst.QueueId.LOGIC;
        IProcessor processor = processors.get(queueId);
        if (processor == null) {
            log.error("找不到可用的消息处理器[{}]", queueId);
            return;
        }
        log.info("收到消息:0x{}", Integer.toHexString(packet.getMsgId()));
        Session session = AttributeUtil.get(channel, SessionKey.SESSION);

        if (session == null) {
            return;
        }
        AbstractHandler handler = msgPool.getHandler(packet.getMsgId());
        handler.setMessage(packet.msg);
        processor.process(handler);
    }

    public IProcessor getProcessor(int queueId) {
        return processors.get(queueId);
    }
}

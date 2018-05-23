package com.wanart.client;

import com.wanart.core.base.common.AttributeUtil;
import com.wanart.core.constant.GameConst;
import com.wanart.core.network.AbstractHandler;
import com.wanart.core.network.IMessageAndHandler;
import com.wanart.core.network.INetworkConsumer;
import com.wanart.core.network.IProcessor;
import com.wanart.core.network.codec.Packet;
import com.wanart.server.Session;
import com.wanart.server.SessionKey;
import com.wanart.server.processor.LogicProcessor;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

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

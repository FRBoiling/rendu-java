package server;

import core.network.IMessageAndHandler;
import core.network.INetworkConsumer;
import core.network.IProcessor;
import core.network.codec.Packet;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: server
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/23 0023 1:24
 * @version: V1.0
 */
@Slf4j
public class MessageRouter implements INetworkConsumer {


    private Map<Integer, IProcessor> processors = new HashMap<>(10);

    private IMessageAndHandler msgPool;

    public MessageRouter(IMessageAndHandler msgPool) {
        this.msgPool = msgPool;
    }

    public void registerProcessor(int queueId, IProcessor consumer) {
        processors.put(queueId, consumer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void consume(Packet packet, Channel channel) {

        //将消息分发到指定的队列中，该队列有可能在同一个进程，也有可能不在同一个进程

        int queueId = 1;

        IProcessor processor = processors.get(queueId);
        if (processor == null) {
            log.error("找不到可用的消息处理器[{}]", queueId);
            return;
        }
        log.debug("收到消息:" + packet.getMsgId());
//        Session session = AttributeUtil.get(channel, SessionKey.SESSION);
//
//        if (session == null) {
//            return;
//        }
//
//        AbstractHandler handler = msgPool.getHandler(msg.getClass().getName());
//        handler.setMessage(msg);
//        handler.setParam(session);
//        log.debug("收到消息:" + msg);
//
//        processor.process(handler);

    }

    public IProcessor getProcessor(int queueId) {
        return processors.get(queueId);
    }

}

package com.wanart.server.gameserver;

import com.wanart.core.constant.GameConst;
import com.wanart.core.network.IMessageAndHandler;
import com.wanart.core.network.INetworkConsumer;
import com.wanart.core.network.IProcessor;
import com.wanart.core.network.codec.Packet;
import com.wanart.server.processor.LogicProcessor;
import com.wanart.server.processor.LoginProcessor;
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
class GameServerMessageRouter implements INetworkConsumer {


    private Map<Integer, IProcessor> processors = new HashMap<>(10);

    private IMessageAndHandler msgPool;

    public GameServerMessageRouter(IMessageAndHandler msgPool) {
        this.msgPool = msgPool;
    }

    public void initRouter()
    {
        //登录和下线
        registerProcessor(GameConst.QueueId.LOGIN_LOGOUT, new LoginProcessor());
        //业务队列
        registerProcessor(GameConst.QueueId.LOGIC, new LogicProcessor());
    }

    private void registerProcessor(int queueId, IProcessor consumer) {
        processors.put(queueId, consumer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void consume(Packet packet, Channel channel) {
//        //此处 消息处理器为多线程的
//        //将消息分发到指定的队列中，该队列有可能在同一个进程，也有可能不在同一个进程
//        int queueId = GameConst.QueueId.LOGIC;
//        IProcessor processor = processors.get(queueId);
//        if (processor == null) {
//            log.error("找不到可用的消息处理器[{}]", queueId);
//            return;
//        }
//        log.info("收到消息:0x{}" , Integer.toHexString(packet.getMsgId()));
//
//        Session session = AttributeUtil.get(channel, SessionKey.SESSION);
//        if (session == null) {
//            return;
//        }
//        AbstractHandler handler = msgPool.getHandler(packet.getMsgId());
//        handler.setMessage(packet.msg);
//        handler.setParam(session);
//        processor.process(handler); //这里执行

        //frTODO:单逻辑线程的话，这里要做的是将消息加入到消息队列
        log.debug("msg go to exec");
    }

    public IProcessor getProcessor(int queueId) {
        return processors.get(queueId);
    }

}

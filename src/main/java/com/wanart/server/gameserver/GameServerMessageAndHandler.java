package com.wanart.server.gameserver;

import com.wanart.core.network.AbstractHandler;
import com.wanart.core.network.IMessageAndHandler;
import com.wanart.server.system.user.handler.TestHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class GameServerMessageAndHandler implements IMessageAndHandler {
    private final HashMap<Integer, Class<? extends AbstractHandler>> handlers = new HashMap<>(10);

    public GameServerMessageAndHandler() {
        protocol.client.gate.C2GIdGenerater.GenerateId();
        register();
    }

    @Override
    public void register() {
        register(protocol.msgId.Id.getInst().getMessageId(protocol.client.gate.C2G.C2GTest1.class), TestHandler.class);
//        register(Id.getInst().getMessageId(C2G.C2GTest2.class), TestHandler2.class);
//        register(Id.getInst().getMessageId(C2G.C2GTest3.class), TestHandler3.class);
    }

    @Override
    public AbstractHandler getHandler(int messageId) {
        Class<? extends AbstractHandler> clazz = handlers.get(messageId);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void register(int messageId, Class<? extends AbstractHandler> handler) {
        if (handlers.containsKey(messageId)) {
            log.debug("add handler failed: msgId {0}", messageId);
            return;
        }
        handlers.put(messageId, handler);
    }


}
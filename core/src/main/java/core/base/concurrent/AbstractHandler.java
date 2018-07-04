package core.base.concurrent;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.concurrent.queue.IMessageQueue;
import core.base.concurrent.queue.IQueueDriverAction;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/23 0023 0:44
 * @version: V1.0
 */
@Data
@Slf4j
public class AbstractHandler <T> implements IQueueDriverAction {
    protected T message;

    protected Object session;
    IMessageQueue<IQueueDriverAction> actionQueue;

    @Override
    public int getQueueId() {
        return 0;
    }

    @Override
    public void setQueueId(int queueId) {

    }

    @Override
    public IMessageQueue<IQueueDriverAction> getActionQueue() {
        return actionQueue;
    }

    @Override
    public void setActionQueue(IMessageQueue<IQueueDriverAction> actionQueue) {
        this.actionQueue = actionQueue;
    }

    @Override
    public Object getParam() {
        return null;
    }

    @Override
    public void setParam(Object param) {
        session = param;
    }

    @Override
    public void doAction() throws InvalidProtocolBufferException {

    }
}

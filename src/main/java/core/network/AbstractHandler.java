package core.network;

import core.base.concurrent.command.IQueueDriverCommand;
import core.base.concurrent.queue.ICommandQueue;
import core.network.codec.PacketDecoder;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class AbstractHandler <T> implements IQueueDriverCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacketDecoder.class);
    @Override
    public int getQueueId() {
        return 0;
    }

    @Override
    public void setQueueId(int queueId) {

    }

    @Override
    public ICommandQueue<IQueueDriverCommand> getCommandQueue() {
        return null;
    }

    @Override
    public void setCommandQueue(ICommandQueue<IQueueDriverCommand> commandQueue) {

    }

    @Override
    public Object getParam() {
        return null;
    }

    @Override
    public void setParam(Object param) {

    }

    @Override
    public void doAction() {
        LOGGER.debug ("doAction");
//        try {
//            long time = System.currentTimeMillis();
//            if (filter != null && !filter.before(this)) {
//                return;
//            }
//            doAction();
//            log.warn(this.getClass().getSimpleName() + "耗时：" + (System.currentTimeMillis() - time) + "ms");
//            if (filter != null) {
//                filter.after(this);
//            }
//        } catch (Throwable e) {
//            log.error("命令执行错误", e);
//        }
    }
}

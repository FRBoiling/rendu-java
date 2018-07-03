package core.base.common;


import core.base.concurrent.IQueueDriverAction;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.network
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 22:22
 * @version: V1.0
 */
public interface IProcessor {
    /**
     * process
     *
     * @param handler
     */
    void process(IQueueDriverAction handler);
}
package com.wanart.core.network;

import com.wanart.core.base.concurrent.command.IQueueDriverCommand;

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
    void process(IQueueDriverCommand handler);
}
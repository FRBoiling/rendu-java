package core.base.concurrent;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.base
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 22:47
 * @version: V1.0
 */

@FunctionalInterface
public interface IAction extends Runnable{
    /**
     * 执行
     */
    void doAction() throws InvalidProtocolBufferException;

    /**
     * 运行
     */
    @Override
    default void run() {
        try {
            doAction();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}

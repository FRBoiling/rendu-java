package core.base.concurrent.command;

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
public interface ICommand extends Runnable{
    /**
     * 执行
     */
    void doAction();

    /**
     * 运行
     */
    @Override
    default void run() {
        doAction();
    }
}

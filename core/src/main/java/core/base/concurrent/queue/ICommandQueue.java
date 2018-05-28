package core.base.concurrent.queue;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: core.base.concurrent.queue
 * @Description:
 *  * 任务队列接口
 *  * 所有实现该接口的队列都应该自己保证其线程安全
 *  *
 *  * @param <V>
 * @author: Boiling
 * @date: 2018/4/22 0022 23:05
 * @version: V1.0
 */
public interface ICommandQueue <V>{
    /**
     * 下一执行命令
     *
     * @return V
     */
    V poll();

    /**
     * 增加执行指令
     *
     * @param value value
     * @return boolean
     */
    boolean offer(V value);

    /**
     * 清理
     */
    void clear();

    /**
     * 获取指令数量
     *
     * @return int
     */
    int size();

    /**
     * 是否在运行
     *
     * @return boolean
     */
    boolean isRunning();

    /**
     * 设置运行状态
     *
     * @param running running
     */
    void setRunning(boolean running);

    /**
     * 设置名字
     *
     * @param name name
     */
    void setName(String name);

    /**
     * 获取名字
     *
     * @return String
     */
    String getName();
}

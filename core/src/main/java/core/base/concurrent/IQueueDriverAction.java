package core.base.concurrent;

import core.base.concurrent.queue.IMessageQueue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  *  * 此行为可以放入QueueDriver中执行.
 * User: Boiling
 * Date: 2018-04-23
 * Time: 15:13
 */
public interface IQueueDriverAction extends IAction {

    /**
     * 获取队列id
     *
     * @return int
     */
    int getQueueId();

    /**
     * 设置队列id
     *
     * @param queueId queueId
     */
    void setQueueId(int queueId);

    /**
     * 获取所在队列
     *
     * @return IMessageQueue
     */
    IMessageQueue<IQueueDriverAction> getActionQueue();

    /**
     * 设置所在队列
     *
     * @param commandQueue commandQueue
     */
    void setActionQueue(IMessageQueue<IQueueDriverAction> commandQueue);

    /**
     * 获取一个额外的参数,随便存什么，具体逻辑具体使用，可以不使用该参数
     *
     * @return Object
     */
    Object getParam();

    /**
     * 设置一个额外的参数,随便存什么，具体逻辑具体使用，可以不使用该参数
     *
     * @param param param
     */
    void setParam(Object param);

}

package core.base.concurrent.queue;

import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description: 驱动器
 * User: Boiling
 * Date: 2018-06-08
 * Time: 11:06
 */
@Slf4j
public class QueueDriver implements IDriver {
    /**
     * 队列最大数量
     */
    private int maxQueueSize;
    /**
     * 驱动名称
     */
    private String name;
    /**
     * 队列ID
     */
    private long queueId;
    /**
     * 消息队列
     */
    private final IMessageQueue<IQueueDriverAction> queue;
    /**
     * 任务执行器
     */
    private QueueExecutor executor;


    public QueueDriver(QueueExecutor executor, String name, long id, int maxQueueSize) {
        this.executor = executor;
        this.name = name;
        this.maxQueueSize = maxQueueSize;
        this.queueId = id;
        this.queue = new MessageQueue<IQueueDriverAction>();
        this.queue.setName(name);
    }

    /**
     * 添加一个行为到队列中
     *
     * @param action action
     */
    @Override
    public boolean addAction(IQueueDriverAction action) {

        if (action.getQueueId() > 0 && action.getQueueId() != this.queueId) {
            return false;
        }

        boolean result;
        synchronized (queue) {

            // 队列中的元素已经超过允许的最大个数时，就将改队列清空，丢弃多有的指令
            if (this.maxQueueSize > 0 && queue.size() > this.maxQueueSize) {
                // LOGGER.error("场景驱动[" + this.name + "]-" + queueId + "抛弃指令!" +
                queue.clear();
            }

            result = queue.offer(action);
            if (result) {
                // 设置action的queue属性
                action.setActionQueue(queue);
                if (!queue.isRunning()) {
                    queue.setRunning(true);
                    executor.execute(queue.poll());
                }
            } else {
                log.error("队列添加任务失败");
            }
        }
        return result;
    }

    @Override
    public IMessageQueue<IQueueDriverAction> getActions() {
        return null;
    }

}

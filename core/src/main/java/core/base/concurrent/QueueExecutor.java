package core.base.concurrent;

import core.base.concurrent.IQueueDriverAction;
import core.base.concurrent.queue.IMessageQueue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description: 队列执行器
 * User: Boiling
 * Date: 2018-06-08
 * Time: 11:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class QueueExecutor extends ThreadPoolExecutor {

//    private static final Logger LOGGER = LoggerFactory.getLogger(QueueExecutor.class);

    /**
     * 执行器名称
     */
    private String name;

    /**
     * 最小线程数
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    public QueueExecutor(final String name, int corePoolSize, int maxPoolSize) {

        super(corePoolSize, maxPoolSize, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    AtomicInteger count = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        int curCount = count.incrementAndGet();
                        log.info("创建线程:" + name + "-" + curCount);
                        return new Thread(r, name + "-" + curCount);
                    }
                });
        this.name = name;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
    }

    /**
     * 指定的任务执行完毕后，调用该方法
     *
     * @param task      执行的任务
     * @param throwable 异常
     */
    @Override
    protected void afterExecute(Runnable task, Throwable throwable) {

        super.afterExecute(task, throwable);
        IQueueDriverAction action = (IQueueDriverAction) task;
        IMessageQueue<IQueueDriverAction> queue = action.getActionQueue();

        synchronized (queue) {
            IQueueDriverAction nextAction = queue.poll();
            if (nextAction == null) {
                // 执行完毕后如果队列中没有任务了，那么设置运行标记为false
                queue.setRunning(false);
            } else {
                // 执行完毕后如果队列中还有任务，那么继续执行下一个
                execute(nextAction);
                //LOGGER.error("存在任务，继续执行任务");
            }
        }
    }


}
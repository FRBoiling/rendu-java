package core.base.concurrent.queue;

import core.base.concurrent.IQueueDriverAction;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-03
 * Time: 20:59
 */

public class MessageExecutor {
    private final IMessageQueue<IQueueDriverAction> queue;
    /**
     * 队列最大数量
     */
    private int maxQueueSize;

    public MessageExecutor(String name, int maxQueueSize) {
        this.queue = new MessageQueue<IQueueDriverAction>();
        this.queue.setName(name);
        this.maxQueueSize = maxQueueSize;
    }

    public void execute(){
        while (queue.size()>0){
            queue.poll().run();
        }
    }

    public boolean addAction(IQueueDriverAction action){
        return queue.offer(action);
    }
}

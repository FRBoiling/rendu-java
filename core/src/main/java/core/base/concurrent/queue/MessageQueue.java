package core.base.concurrent.queue;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-08
 * Time: 11:01
 */

public class MessageQueue <V> implements IMessageQueue<V> {

    /**
     * 消息
     */
    private final Queue<V> queueList;

    /**
     * 是否正在运行中
     */
    private boolean running = false;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建一个空队列
     */
    public MessageQueue() {
        queueList = new ArrayDeque<>();
    }

    /**
     * 创建一个空的队列，并用指定的大小初始化该队列
     */
    public MessageQueue(int numElements) {
        queueList = new ArrayDeque<>(numElements);
    }

    /**
     * 下一执行命令
     *
     * @return v
     */
    @Override
    public V poll() {
        //TODO:boiling 消息队列线程安全处理
        return this.queueList.poll();
    }

    /**
     * 增加执行指令
     */
    @Override
    public boolean offer(V value) {
        //TODO:boiling 消息队列线程安全处理
        return this.queueList.offer(value);
    }

    /**
     * 清理
     */
    @Override
    public void clear() {
        this.queueList.clear();
    }

    /**
     * 获取指令数量
     *
     * @return int
     */
    @Override
    public int size() {
        return this.queueList.size();
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

package core.base.sequence;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.network.IResponseHandlerManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * Description: 消息驱动器
 * User: Boiling
 * Date: 2018-07-03
 * Time: 20:40
 */
@Slf4j
public class MessageDriver {

    /**
     * 队列最大数量
     */
    private int maxQueueSize;
    /**
     * 驱动名称
     */
    private String name;

    /**
     * 消息队列
     */
    private final ConcurrentLinkedQueue<Command> tmpMsgQueue;
    private final Queue<Command>  dealMsgQueue;

    IResponseHandlerManager responseMng;

    class Command{
        int msgId;
        byte[] msg;
        public Command(int msgId,byte[] msg){
            this.msgId = msgId;
            this.msg = msg;
        }
    }

    public MessageDriver(int maxQueueSize, String name) {
        this.name = name;
        this.maxQueueSize = maxQueueSize;
        this.tmpMsgQueue = new ConcurrentLinkedQueue();
        this.dealMsgQueue = new ConcurrentLinkedQueue();
    }

    public MessageDriver(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        this.tmpMsgQueue = new ConcurrentLinkedQueue();
        this.dealMsgQueue = new ConcurrentLinkedQueue();
    }

    public void register(String name) {
        this.name = name;
    }

    public void setResponseMng(IResponseHandlerManager responseMng) {
        this.responseMng =responseMng;
    }


    /**
     *  * 添加一个消息到队列中
     *
     * @param msgId
     * @param msg
     * @return
     */
    public boolean addMessage(int msgId, byte[] msg) {
        boolean result;
        Command cmd = new Command(msgId,msg);
        result = tmpMsgQueue.offer(cmd);
        if (!result) {
            log.error("{}队列添加任务失败", name);
        } 
        return result;
    }

    public void update(AbstractSession session){
        while (tmpMsgQueue.size()>0){
            Command msg = tmpMsgQueue.poll();
            dealMsgQueue.offer(msg);
        }
        while (dealMsgQueue.size()>0){
            MessageDriver.Command msg = dealMsgQueue.poll();
            IResponseHandler handler= responseMng.getHandler(msg.msgId);
            if ( handler == null)
            {
                log.info("got an no registered msg:" + msg.msgId);
                return;
            }
            try {
                handler.onResponse(msg.msg,session);
            } catch (InvalidProtocolBufferException e) {
              log.error("msg {} response fail!",msg.msgId);
              e.printStackTrace();
            }
        }
    }

//    public IMessageQueue<IQueueDriverAction> getActions() {
//        return queue;
//    }

}

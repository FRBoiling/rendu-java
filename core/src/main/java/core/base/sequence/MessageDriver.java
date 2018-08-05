package core.base.sequence;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.network.IResponseHandlerManager;
import core.network.codec.Packet;
import lombok.extern.slf4j.Slf4j;
import util.StringUtil;

import java.util.ArrayDeque;
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
    private final ConcurrentLinkedQueue<Packet> tmpMsgQueue;
    private final Queue<Packet> dealMsgQueue;

    private IResponseHandlerManager responseMng;

    public MessageDriver(int maxQueueSize, String name) {
        this.name = name;
        this.maxQueueSize = maxQueueSize;
        this.tmpMsgQueue = new ConcurrentLinkedQueue<Packet>();
        this.dealMsgQueue = new ArrayDeque<Packet>();
    }

    public MessageDriver(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        this.tmpMsgQueue = new ConcurrentLinkedQueue<Packet>();
        this.dealMsgQueue = new ArrayDeque<Packet>();
    }

    public void register(String name) {
        this.name = name;
    }

    public void setResponseMng(IResponseHandlerManager responseMng) {
        this.responseMng = responseMng;
    }

    //    /**
//     *  * 添加一个消息到队列中
//     *
//     * @param msgId
//     * @param msg
//     * @return
//     */
    public boolean addMessage(Packet packet) {
        boolean result = false;
        result = tmpMsgQueue.offer(packet);
        if (!result) {
            log.error("{}队列添加任务失败", name);
        }
        return result;
    }

    //
    public void update(AbstractSession session) {
        while (tmpMsgQueue.size() > 0) {
            Packet msg = tmpMsgQueue.poll();
            dealMsgQueue.offer(msg);
        }
        while (dealMsgQueue.size() > 0) {
            Packet msg = dealMsgQueue.poll();
            if (msg==null){
                log.error("got an null msg packet");
                return;
            }
            IResponseHandler handler = responseMng.getHandler(msg.getMsgId());
            if (handler == null) {
                log.error("got an no registered msg:" + StringUtil.toHexString(msg.getMsgId()));
                return;
            }
            try {
                handler.onResponse(msg,session);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }

        }
    }

//    public IMessageQueue<IQueueDriverAction> getActions() {
//        return queue;
//    }

}

package core.base.common;

import core.base.concurrent.IDriver;
import core.base.concurrent.IQueueDriverAction;
import core.base.concurrent.queue.MessageDriver;
import core.base.concurrent.queue.MessageExecutor;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 14:30
 */
@Slf4j
@Data
public abstract class AbstractSession {

    private Channel channel;

    private boolean isRegistered;

    private int sendDelay;

    private volatile boolean offline = false;

    private ISessionTag tag;

    private final MessageDriver messageDriver;
    private final MessageExecutor messageExecutor;

    public AbstractSession(Channel channel) {
        this.channel = channel;
        isRegistered = false;

        messageDriver = new MessageDriver(1024);
        messageExecutor = new MessageExecutor(1024);
    }

    public void OnConnected()
    {
//        log.info("{}",channel.remoteAddress());
    }

    public void OnDisConnected()
    {
        offline = true;
        if (isRegistered) {
            log.info("{} disconnect ",getKey());
        } else {
            //下线
            log.error("[没有找到会话注册信息]");
        }
    }

//
//    public Channel getChannel() {
//        return channel;
//    }
//
//    public void setChannel(Channel channel) {
//        this.channel = channel;
//    }

    public String getIP() {
        if (channel == null) {
            return "";
        }
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
        if (address == null) {
            return "";
        }
        return address.getAddress().getHostAddress();
    }

    public void clearAttribute() {
        if (channel == null) {
            return;
        }
//      AttributeUtil.set(channel, ISessionAttributeKey.UID, null);
    }

    public void close() {
        if (channel == null) {
            return;
        }
        clearAttribute();
        try {
            if (channel.isActive() || channel.isOpen()) {
                channel.close().sync();
            }
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

//    public void closeByHttp() {
//        if (channel == null) {
//            return;
//        }
//        try {
//            channel.close().sync();
//        } catch (InterruptedException e) {
//            log.error("", e);
//        }
//    }
    public void update(){
        synchronized (messageDriver.getActions()) {
           while (messageDriver.getActions().size()>0){
               IQueueDriverAction action = messageDriver.getActions().poll();
               messageExecutor.addAction(action);
           }
        }
        messageExecutor.execute();
    }

    public void sendMessage(Object msg) {
        channel.writeAndFlush(msg);
    }

    public abstract void sendHeartBeat();

    public String getKey() {
        if ( tag == null){
            return "";
        }
        return tag.getKey();
    }
}

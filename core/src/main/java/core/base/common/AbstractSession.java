package core.base.common;

import com.google.protobuf.MessageLite;
import core.base.model.ServerTag;
import core.base.sequence.MessageDriver;
import core.network.IResponseHandlerManager;
import core.network.codec.Packet;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-04-23
 * Time: 14:30
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractSession {

    private Channel channel;

    private boolean isRegistered;

    private int sendDelay;

    private volatile boolean offline = false;

    private ISessionTag tag;

    private MessageDriver messageDriver;

    @Override
    public int hashCode() {
        return channel.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractSession) {
            AbstractSession p = (AbstractSession) obj;
            return p.tag.equals(this.tag)&&p.getChannel().equals(this.channel) ;
        } else {
            return false;
        }
    }

    public AbstractSession(Channel channel) {
        this.channel = channel;
        isRegistered = false;
        messageDriver = new MessageDriver(1024);
    }

    public void setResponseMng(IResponseHandlerManager responseMng){
        this.messageDriver.setResponseMng(responseMng);
    }

    public void onConnected()
    {
        //TODO:会话层连接处理
//        log.info("{}",channel.remoteAddress());
    }

    public void onDisConnected()
    {
        offline = true;
        clearAttribute();
        if (isRegistered) {
            log.info("{} disconnect :{}",getTag().toString(),channel.toString());
        } else {
            //下线
            log.error("[没有找到会话注册信息]:{} disconnect",channel.toString());
        }
    }

     String getIP() {
        if (channel == null) {
            return "";
        }
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
        if (address == null) {
            return "";
        }
        return address.getAddress().getHostAddress();
    }

    private void clearAttribute() {
        if (channel == null) {
            return;
        }
        AttributeUtil.set(channel, SessionKey.SESSION, null);
    }

    /**
     * 关闭连接
     */
    public void closeConnection() {
        if (channel == null) {
            return;
        }
        try {
            if (channel.isActive() || channel.isOpen()) {
                channel.close().sync();
            }
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

    public void update(){
        messageDriver.update(this);
    }

    public void sendMessage(MessageLite msg) {
        Packet packet = new Packet();
        packet.setPlayerId(0).setMsgId(Id.getInst().getMessageId(msg.getClass())).setMsg(msg.toByteArray());
        channel.writeAndFlush(packet);
    }

    public void sendMessage(MessageLite msg,int playerId) {
        Packet packet = new Packet();
        packet.setPlayerId(playerId).setMsgId(Id.getInst().getMessageId(msg.getClass())).setMsg(msg.toByteArray());
        channel.writeAndFlush(packet);
    }

    public abstract void sendHeartBeat();

    public void sendRegister(ServerTag tag)
    {
        log.debug("send register msg : {} register to {}",tag.toString(),getTag().toString());
        ServerRegister.Server_Tag.Builder serverTag = ServerRegister.Server_Tag.newBuilder();
        serverTag.setServerType(tag.getType().ordinal());
        serverTag.setGroupId(tag.getGroupId());
        serverTag.setSubId(tag.getSubId());

        ServerRegister.MSG_Server_Register.Builder builder = ServerRegister.MSG_Server_Register.newBuilder();
        builder.setTag(serverTag);

        sendMessage(builder.build());
    }
}

package gate.global;

import core.base.common.AbstractSession;
import core.base.common.AttributeUtil;
import core.base.common.SessionKey;
import core.network.INetworkConsumer;
import core.network.IResponseHandlerManager;
import core.network.codec.Packet;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */
@Slf4j
public class GlobalServerMsgRouter implements INetworkConsumer {
    @Override
    public void consume(Packet packet, Channel channel) {
        GlobalServerSession session =(GlobalServerSession)AttributeUtil.get(channel, SessionKey.SESSION);
        //TODO:boil 单逻辑线程的话，这里要做的是将消息加入到消息队列
        log.info("msg go to exec");
    }
}

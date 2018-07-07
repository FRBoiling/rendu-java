package core.network;

import core.base.common.AbstractSession;
import core.base.common.AttributeUtil;
import core.base.common.SessionKey;
import core.network.codec.Packet;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-08
 * Time: 16:24
 */
@Slf4j
public class MsgRouter implements INetworkConsumer {
    @Override
    public void consume(Packet packet, Channel channel) {
        AbstractSession session = AttributeUtil.get(channel, SessionKey.SESSION);
        if (session == null) {
            return;
        }
        session.getMessageDriver().addMessage(packet.getMsgId(),packet.getMsg());
    }
}

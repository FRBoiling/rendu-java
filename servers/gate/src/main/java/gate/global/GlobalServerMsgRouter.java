package gate.global;

import core.base.common.AbstractSession;
import core.base.common.AttributeUtil;
import core.base.common.SessionKey;
import core.base.concurrent.queue.QueueDriver;
import core.network.AbstractMsgRouter;
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
public class GlobalServerMsgRouter extends AbstractMsgRouter {
    public GlobalServerMsgRouter(IResponseHandlerManager responseHandlerManager, QueueDriver queueDriver) {
        super(responseHandlerManager, queueDriver);
    }
}

package global.gate;

import core.base.common.AbstractSession;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:19
 */

public class GateServerSession extends AbstractSession {
    public GateServerSession(Channel channel) {
        super(channel);
    }

    @Override
    public void OnConnected() {

    }
}

package manager.gate;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import io.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:19
 */

public class GateServerSession extends AbstractSession {
    GateServerSession(Channel channel) {
        super(channel);
        ServerTag tag = new ServerTag();
        tag.setTag(ServerType.Gate, 0, 0);
        setTag(tag);
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void sendHeartBeat() {

    }
}

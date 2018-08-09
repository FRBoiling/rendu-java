package global.zone;

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

public class ZoneServerSession extends AbstractSession {
     ZoneServerSession(Channel channel) {
        super(channel);
        ServerTag tag =new ServerTag();
        tag.setTag(ServerType.Zone,0,0);
        setTag(tag);
    }

    @Override
    public void onConnected() {
        super.onConnected();
    }

    @Override
    public void sendHeartBeat() {

    }
}

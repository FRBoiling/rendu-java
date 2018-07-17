package gate.client;

import core.base.common.AbstractSession;
import core.base.model.ClientTag;
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

public class ClientSession extends AbstractSession {
    public ClientSession(Channel channel) {
        super(channel);
        ClientTag tag =new ClientTag();
//        tag.setTag(ServerType.Gate,0,0);
        setTag(tag);
    }

    @Override
    public void OnConnected() {

    }

    @Override
    public void sendHeartBeat() {

    }
}

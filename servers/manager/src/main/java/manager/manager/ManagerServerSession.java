package manager.manager;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import manager.Context;
import protocol.zone.global.Z2GM;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:14
 */
@Slf4j
public class ManagerServerSession extends AbstractSession {
    ManagerServerSession(Channel channel) {
        super(channel);
        ServerTag tag = new ServerTag();
        tag.setTag(ServerType.Manager, 0, 0);
        setTag(tag);
    }

    public void OnConnected() {
        super.OnConnected();
        sendRegister(Context.tag);
    }

    public void OnDisConnected() {
        super.OnDisConnected();
    }

    @Override
    public void sendHeartBeat() {
        Z2GM.MSG_Z2GM_HEARTBEAT.Builder builder = Z2GM.MSG_Z2GM_HEARTBEAT.newBuilder();
        sendMessage(builder.build());
    }
}

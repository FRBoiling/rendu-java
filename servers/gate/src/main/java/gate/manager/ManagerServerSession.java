package gate.manager;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import gate.Context;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.manager.G2M;

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

    public void onConnected() {
        super.onConnected();
        sendRegister(Context.tag,null);
    }

    public void onDisConnected() {
        super.onDisConnected();
    }

    @Override
    public void sendHeartBeat() {
        G2M.MSG_G2M_HEARTBEAT.Builder builder = G2M.MSG_G2M_HEARTBEAT.newBuilder();
        sendMessage(builder.build());
    }
}

package gate.zone;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import gate.Context;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.zone.G2Z;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:14
 */
@Slf4j
public class ZoneServerSession extends AbstractSession {
    ZoneServerSession(Channel channel) {
        super(channel);
        ServerTag tag = new ServerTag();
        tag.setTag(ServerType.Zone, 0, 0);
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
        G2Z.MSG_G2Z_HEARTBEAT.Builder builder = G2Z.MSG_G2Z_HEARTBEAT.newBuilder();
        sendMessage(builder.build());
    }
}

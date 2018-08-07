package zone.relation;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import protocol.zone.global.Z2GM;
import protocol.zone.relation.Z2R;
import zone.Context;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:14
 */
@Slf4j
public class RelationServerSession extends AbstractSession {
    RelationServerSession(Channel channel) {
        super(channel);
        ServerTag tag = new ServerTag();
        tag.setTag(ServerType.Zone, 0, 0);
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
        Z2R.MSG_Z2R_HEARTBEAT.Builder builder = Z2R.MSG_Z2R_HEARTBEAT.newBuilder();
        sendMessage(builder.build());
    }
}

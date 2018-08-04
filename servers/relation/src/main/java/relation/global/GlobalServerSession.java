package relation.global;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import protocol.relation.global.R2GM;
import relation.Context;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:14
 */
@Slf4j
public class GlobalServerSession extends AbstractSession {
    GlobalServerSession(Channel channel) {
        super(channel);
        ServerTag tag = new ServerTag();
        tag.setTag(ServerType.Global, 0, 0);
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
        R2GM.MSG_R2GM_HEARTBEAT.Builder builder = R2GM.MSG_R2GM_HEARTBEAT.newBuilder();
        sendMessage(builder.build());
    }
}

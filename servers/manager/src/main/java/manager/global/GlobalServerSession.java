package manager.global;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import manager.Context;
import protocol.manager.global.M2GM;

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

    public void onConnected() {
        super.onConnected();
        sendRegister(Context.tag);
    }

    public void onDisConnected() {
        super.onDisConnected();
    }

    @Override
    public void sendHeartBeat() {
        M2GM.MSG_M2GM_HEARTBEAT.Builder builder = M2GM.MSG_M2GM_HEARTBEAT.newBuilder();
        sendMessage(builder.build());
    }
}

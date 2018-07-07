package relation.global;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import protocol.relation.global.R2GM;
import protocol.server.register.ServerRegister;
import relation.RelationServiceContext;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:14
 */
@Slf4j
public class GlobalServerSession extends AbstractSession {
    public GlobalServerSession(Channel channel) {
        super(channel);
        ServerTag tag =new ServerTag();
        tag.setTag(ServerType.Global,0,0);
        setTag(tag);
    }
    public void OnConnected() {
        super.OnConnected();
        sendRegister();
    }
    public void OnDisConnected(){
        super.OnDisConnected();
    }

    @Override
    public void sendHeartBeat() {
        R2GM.MSG_R2GM_HEARTBEAT.Builder builder = R2GM.MSG_R2GM_HEARTBEAT.newBuilder();
        sendMessage(builder.build());
    }

    public void sendRegister()
    {
        ServerRegister.MSG_REQ_Server_Register.Builder builder = ServerRegister.MSG_REQ_Server_Register.newBuilder();
        builder.setGroupId(RelationServiceContext.tag.getGroupId());
        builder.setSubId(RelationServiceContext.tag.getSubId());
        builder.setServerType(RelationServiceContext.tag.getType().ordinal());

        sendMessage(builder.build());
    }
}

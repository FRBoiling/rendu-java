package gate.global;

import core.base.common.AbstractSession;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;
import protocol.gate.global.G2GM;

import java.util.concurrent.TimeUnit;

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
    }
    public void OnConnected() {
        super.OnConnected();
        SendRegister();
    }
    public void OnDisConnected(){
        super.OnDisConnected();
        getChannel().closeFuture();
    }
    public void SendRegister()
    {
        G2GM.MSG_GM2G_REQ_Register.Builder builder = G2GM.MSG_GM2G_REQ_Register.newBuilder();
        builder.setId(1);
        sendMessage(builder.build());
        log.info("SendRegister----------{}",1);
    }
}

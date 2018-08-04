package gate.client;

import core.base.common.AbstractSession;
import core.base.model.ClientTag;
import dataObject.AccountObject;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import protocol.client.c2g.C2G;

import java.net.PortUnreachableException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-31
 * Time: 16:19
 */
@Getter
@Setter
public class ClientSession extends AbstractSession {

    ClientSession(Channel channel) {
        super(channel);
        ClientTag tag = new ClientTag();
        setTag(tag);
    }

    @Override
    public void OnConnected() {

    }

    @Override
    public void sendHeartBeat() {

    }

    public C2G.MSG_CG_CREATE_CHARACTER reqCreateMsg = null;

    AccountObject accountObject = null;

    void login() {
        ClientLoginMng.getInstance().loadAccount(this);
    }

}

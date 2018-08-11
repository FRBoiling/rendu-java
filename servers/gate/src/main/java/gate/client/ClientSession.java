package gate.client;

import core.base.common.AbstractSession;
import core.base.model.ClientTag;
import gamedb.pojo.RolePOJO;
import gamedb.pojo.account.AccountPOJO;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import protocol.client.Client.*;

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
    public void onConnected() {
        super.onConnected();
    }

    @Override
    public void sendHeartBeat() {

    }

    public MSG_CG_CREATE_ROLE reqCreateMsg = null;

    AccountPOJO accountPOJO = null;

    void login() {
        ClientLoginMng.getInstance().loadAccount(this);
    }
}

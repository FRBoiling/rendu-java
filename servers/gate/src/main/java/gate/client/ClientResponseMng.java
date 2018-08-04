package gate.client;

import core.network.IResponseHandlerManager;
import gate.client.response.ResponseUserLogin;
import lombok.extern.slf4j.Slf4j;
import protocol.client.c2g.C2G;
import protocol.msgId.Id;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-29
 * Time: 16:26
 */
@Slf4j
public class ClientResponseMng implements IResponseHandlerManager {

    ClientResponseMng() {
        register();
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(C2G.MSG_CG_USER_LOGIN.class), ResponseUserLogin.class);
    }
}

package gate.client;

import core.network.IResponseHandlerManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-29
 * Time: 16:26
 */
@Slf4j
public class ClientResponseMng implements IResponseHandlerManager {

    public ClientResponseMng() {
        register();
    }

    @Override
    public void register() {
//        register(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register.class), ResponseRegister.class);
//        register(Id.getInst().getMessageId(G2GM.MSG_G2GM_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

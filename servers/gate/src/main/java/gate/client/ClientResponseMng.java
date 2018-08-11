package gate.client;

import core.network.IResponseHandlerManager;
import gate.client.response.ResponseCreateRole;
import gate.client.response.ResponseReconnectLogin;
import gate.client.response.ResponseUserLogin;
import lombok.extern.slf4j.Slf4j;
import protocol.client.Client.*;
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

    private static ClientResponseMng INSTANCE = new ClientResponseMng();

    public static ClientResponseMng getInstance() {
        return INSTANCE;
    }

    private ClientResponseMng() {
        registerHandlers();
    }

    @Override
    public void registerHandlers() {
        registerHandler(Id.getInst().getMessageId(MSG_CG_USER_LOGIN.class), ResponseUserLogin.class);
        registerHandler(Id.getInst().getMessageId(MSG_CG_CREATE_ROLE.class), ResponseCreateRole.class);
        registerHandler(Id.getInst().getMessageId(MSG_CG_RECONNECT_LOGIN.class), ResponseReconnectLogin.class);
    }
}

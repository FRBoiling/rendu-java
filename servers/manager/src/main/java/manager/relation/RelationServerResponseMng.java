package manager.relation;

import core.network.IResponseHandlerManager;
import lombok.extern.slf4j.Slf4j;
import manager.connectionManager.ResponseRegister;
import manager.relation.response.ResponseHeartBeat;
import protocol.msgId.Id;
import protocol.relation.manager.R2M;
import protocol.server.register.ServerRegister;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-29
 * Time: 16:26
 */
@Slf4j
public class RelationServerResponseMng implements IResponseHandlerManager {

    RelationServerResponseMng() {
        register();
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register.class), ResponseRegister.class);
        register(Id.getInst().getMessageId(R2M.MSG_R2M_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

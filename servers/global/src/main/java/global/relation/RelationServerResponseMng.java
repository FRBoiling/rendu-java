package global.relation;

import core.network.IResponseHandlerManager;
import global.connectionManager.ResponseRegister;
import global.relation.response.ResponseHeartBeat;
import lombok.extern.slf4j.Slf4j;
import protocol.msgId.Id;
import protocol.relation.global.R2GM;
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
        register(Id.getInst().getMessageId(R2GM.MSG_R2GM_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

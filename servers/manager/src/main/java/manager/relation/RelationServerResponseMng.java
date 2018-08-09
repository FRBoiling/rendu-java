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

    private static RelationServerResponseMng INSTANCE = new RelationServerResponseMng();

    public static RelationServerResponseMng getInstance() {
        return INSTANCE;
    }

    private RelationServerResponseMng() {
        registerHandlers();
    }

    @Override
    public void registerHandlers() {
        registerHandler(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register.class), ResponseRegister.class);
        registerHandler(Id.getInst().getMessageId(R2M.MSG_R2M_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

package relation.relation;

import core.network.IResponseHandlerManager;
import protocol.msgId.Id;
import protocol.relation.relation.R2R;
import protocol.server.register.ServerRegister;
import relation.connectionManager.ResponseRegisterReturn;
import relation.relation.response.ResponseHeartBeat;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

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
        registerHandler(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register_Return.class), ResponseRegisterReturn.class);
//        registerHandler(Id.getInst().getMessageId(R2R.MSG_R2R_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

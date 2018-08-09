package relation.relation;

import core.network.IResponseHandlerManager;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;
import relation.connectionManager.ResponseRegisterReturn;

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
    }
}

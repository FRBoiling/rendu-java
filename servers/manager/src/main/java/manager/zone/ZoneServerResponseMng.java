package manager.zone;

import core.network.IResponseHandlerManager;
import manager.connectionManager.ResponseRegister;
import manager.relation.response.ResponseHeartBeat;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;
import protocol.zone.manager.Z2M;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:37
 */

public class ZoneServerResponseMng implements IResponseHandlerManager {
    ZoneServerResponseMng() {
        register();
    }

    @Override
    public void register() {
        register(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register.class), ResponseRegister.class);
        register(Id.getInst().getMessageId(Z2M.MSG_Z2M_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

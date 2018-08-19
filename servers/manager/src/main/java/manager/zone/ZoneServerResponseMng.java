package manager.zone;

import core.network.IResponseHandlerManager;
import manager.connectionManager.ResponseRegister;
import manager.zone.response.*;
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

    private static ZoneServerResponseMng INSTANCE = new ZoneServerResponseMng();

    public static ZoneServerResponseMng getInstance() {
        return INSTANCE;
    }

    private ZoneServerResponseMng() {
        registerHandlers();
    }

    @Override
    public void registerHandlers() {
        registerHandler(Id.getInst().getMessageId(ServerRegister.MSG_Server_Register.class), ResponseRegister.class);
        registerHandler(Id.getInst().getMessageId(Z2M.MSG_Z2M_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

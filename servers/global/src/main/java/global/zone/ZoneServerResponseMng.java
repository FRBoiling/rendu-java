package global.zone;

import core.network.IResponseHandlerManager;
import global.connectionManager.ResponseRegister;
import global.zone.response.ResponseHeartBeat;
import lombok.extern.slf4j.Slf4j;
import protocol.msgId.Id;
import protocol.server.register.ServerRegister;
import protocol.zone.global.Z2GM;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-29
 * Time: 16:26
 */
@Slf4j
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
        registerHandler(Id.getInst().getMessageId(Z2GM.MSG_Z2GM_HEARTBEAT.class), ResponseHeartBeat.class);
    }
}

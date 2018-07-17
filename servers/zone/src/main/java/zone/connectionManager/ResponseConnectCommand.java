package zone.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.sequence.IResponseHandler;
import core.network.codec.Packet;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;
import zone.ZoneServiceContext;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-07
 * Time: 17:35
 **/
@Slf4j
public class ResponseConnectCommand implements IResponseHandler {
    @Override
    public void onResponse(Packet packet, AbstractSession session) throws InvalidProtocolBufferException {
        ServerRegister.MSG_Server_Connect_Command command = ServerRegister.MSG_Server_Connect_Command.parseFrom(packet.getMsg());
        ServerType serverType =  ServerType.values()[command.getTag().getServerType()];
        int groupId = command.getTag().getGroupId();
        int subId = command.getTag().getSubId();

        String ip = command.getInfo().getIp();
        int port = command.getInfo().getPort();

        ServerTag tag = new ServerTag();
        tag.setTag(serverType,groupId,subId);

        ZoneServiceContext.connectMng.connect(tag,ip,port);
    }




}

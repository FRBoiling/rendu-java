package core.base.serviceframe;

import core.base.common.ISessionTag;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import protocol.server.register.ServerRegister;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-09
 * Time: 14:15
 **/
public interface IConnectManager {
    /**
     * init
     */
    void init();

    /**
     * start
     */
    void start();

    /**
     * stop
     */
    void stop();

    /**
     * update
     */
    void update(long dt);

    /*
     * 连接
     */
    void connect(ServerTag tag,String ip,int port);

    default void executeConnectCommand(ServerRegister.MSG_Server_Connect_Command command) {
        ServerType serverType =  ServerType.values()[command.getTag().getServerType()];
        int groupId = command.getTag().getGroupId();
        int subId = command.getTag().getSubId();

        String ip = command.getInfo().getIp();
        int port = command.getInfo().getPort();

        ServerTag tag = new ServerTag();
        tag.setTag(serverType,groupId,subId);

        connect(tag,ip,port);
    }


    default ServerRegister.MSG_Server_Connect_Command.Builder getConnectionCommand(ServerRegister.Server_Tag.Builder tag, ServerRegister.Connect_Info.Builder info) {
        ServerRegister.MSG_Server_Connect_Command.Builder command = ServerRegister.MSG_Server_Connect_Command.newBuilder();
//        command.setConnectType(type);
        command.setTag(tag);
        command.setInfo(info);
        return command;
    }

    default ServerRegister.Server_Tag.Builder getServerTag(ServerType type, int groupId, int subId) {
        ServerRegister.Server_Tag.Builder tagBuilder = ServerRegister.Server_Tag.newBuilder();
        tagBuilder.setServerType(type.ordinal());
        tagBuilder.setGroupId(groupId);
        tagBuilder.setSubId(subId);
        return tagBuilder;
    }

    default ServerRegister.Connect_Info.Builder getServerConnectInfo(String ip, int port) {
        ServerRegister.Connect_Info.Builder infoBuilder = ServerRegister.Connect_Info.newBuilder();
        infoBuilder.setIp(ip);
        infoBuilder.setPort(port);
        return infoBuilder;
    }
}

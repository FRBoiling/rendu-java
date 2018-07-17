package global.connectionManager;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.serviceframe.IConnectManager;
import global.gate.GateServer;
import global.gate.GateServerSessionMng;
import global.manager.ManagerServer;
import global.manager.ManagerServerSessionMng;
import global.relation.RelationServer;
import global.zone.ZoneServer;
import global.zone.ZoneServerSession;
import global.zone.ZoneServerSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-07
 * Time: 15:48
 **/

@Slf4j
public class ConnectManager implements IConnectManager {
    private GateServer gateServer;
    private ManagerServer managerServer;
    private ZoneServer zoneServer;
    private RelationServer relationServer;

    @Override
    public void init() {
        gateServer = createGateServer();
        gateServer.init(null);
        managerServer = createManagerServer();
        managerServer.init(null);
        zoneServer = createZoneServer();
        zoneServer.init(null);
        relationServer = createRelationServer();
        relationServer.init(null);
    }

    @Override
    public void start() {
        zoneServer.start();
        relationServer.start();
        gateServer.start();
        managerServer.start();
    }

    @Override
    public void stop() {
        zoneServer.stop();
        relationServer.stop();
        gateServer.stop();
        managerServer.stop();
    }

    @Override
    public void update() {
        zoneServer.update();
        relationServer.update();
        gateServer.update();
        managerServer.update();
    }

    private GateServer createGateServer() {
        try {
            gateServer = new GateServer();
            return gateServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ManagerServer createManagerServer() {
        try {
            managerServer = new ManagerServer();
            return managerServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ZoneServer createZoneServer() {
        try {
            zoneServer = new ZoneServer();
            return zoneServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private RelationServer createRelationServer() {
        try {
            relationServer = new RelationServer();
            return relationServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    public void sendConnectionCommand(ServerTag tag){
        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        switch (tag.getType()){
            case Manager:
                notifyConnect2Zone(tag,dateList);
//                notifyConnect2Gate(tag,dateList);
                break;
            case Gate:
                break;
            case Zone:
                notifyZone2Connect(tag,dateList);
                break;
            case Relation:
                break;
        }
    }

    private void notifyZone2Connect(ServerTag zoneTag,DataList dataList)
    {
        ZoneServerSession session = (ZoneServerSession)ZoneServerSessionMng.getInstance().getRegisterSession(zoneTag.getKey());
        if (session !=null){
            //notify zone manager info
            for (AbstractSession manager:ManagerServerSessionMng.getInstance().getRegisterSessions().values()){
                ServerTag tag = (ServerTag) manager.getTag();
                if (manager.isRegistered() && tag.getGroupId() == zoneTag.getGroupId())
                {
                    Data serviceData =dataList.getData(tag.getKey());
                    String ip = serviceData.getString("ip");
                    int listenPort = serviceData.getInteger("zonePort");
                    ServerRegister.Server_Tag.Builder tagBuilder = getServerTag(tag.getType(),tag.getGroupId(),tag.getSubId());
                    ServerRegister.Connect_Info.Builder infoBuilder = getServerConnectInfo(ip,listenPort);
                    ServerRegister.MSG_Server_Connect_Command.Builder commandBuilder =  getConnectionCommand(tagBuilder,infoBuilder);
                    log.info("notifyZone2Connect : {} connect to {}",session.getKey(),tag.getKey());
                    session.sendMessage(commandBuilder.build());
                }
            }

            //TODO: Boil notify zone other server info

        }
    }

    private void notifyConnect2Zone(ServerTag tag,DataList dataList)
    {
        //notify serverInfo to zone
        Data serviceData =dataList.getData(tag.getKey());
        String ip = serviceData.getString("ip");
        int listenPort = serviceData.getInteger("zonePort");
        ServerRegister.Server_Tag.Builder tagBuilder = getServerTag(tag.getType(),tag.getGroupId(),tag.getSubId());
        ServerRegister.Connect_Info.Builder infoBuilder = getServerConnectInfo(ip,listenPort);
        ServerRegister.MSG_Server_Connect_Command.Builder commandBuilder =  getConnectionCommand(tagBuilder,infoBuilder);
        for (AbstractSession session:ZoneServerSessionMng.getInstance().getRegisterSessions().values()){
            if (session.isRegistered())
            {
                log.info("notifyConnect2Zone : {} connect to {}",session.getKey(),tag.getKey());
                session.sendMessage(commandBuilder.build());
            }
        }
    }


    private void notifyConnect2Gate(ServerTag tag,DataList dataList)
    {
        Data serviceData =dataList.getData(tag.getKey());
        String ip = serviceData.getString("ip");
        int listenPort = serviceData.getInteger("gatePort");
        ServerRegister.Server_Tag.Builder tagBuilder = getServerTag(tag.getType(),tag.getGroupId(),tag.getSubId());
        ServerRegister.Connect_Info.Builder infoBuilder = getServerConnectInfo(ip,listenPort);
        ServerRegister.MSG_Server_Connect_Command.Builder commandBuilder =  getConnectionCommand(tagBuilder,infoBuilder);
        for (AbstractSession session:GateServerSessionMng.getInstance().getRegisterSessions().values()){
            if (session.isRegistered())
            {
                session.sendMessage(commandBuilder.build());
            }
        }
    }

}

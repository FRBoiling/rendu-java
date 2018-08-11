package global.connectionManager;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import core.base.common.AbstractSession;
import core.base.common.ISessionTag;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.IConnectionManager;
import global.gate.GateAcceptor;
import global.gate.GateServerSessionMng;
import global.manager.ManagerAcceptor;
import global.manager.ManagerServerSessionMng;
import global.relation.RelationAcceptor;
import global.relation.RelationServerSessionMng;
import global.zone.ZoneAcceptor;
import global.zone.ZoneServerSessionMng;
import lombok.extern.slf4j.Slf4j;
import protocol.server.register.ServerRegister;

import java.util.Map;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-07
 * Time: 15:48
 **/

@Slf4j
public class ConnectionManager implements IConnectionManager {
    private GateAcceptor gateAcceptor;
    private ManagerAcceptor managerAcceptor;
    private ZoneAcceptor zoneAcceptor;
    private RelationAcceptor relationAcceptor;

    @Override
    public void init() {
        gateAcceptor = createGateServer();
        gateAcceptor.init(null);

        zoneAcceptor = createZoneServer();
        zoneAcceptor.init(null);

        managerAcceptor = createManagerServer();
        managerAcceptor.init(null);

        relationAcceptor = createRelationServer();
        relationAcceptor.init(null);
    }

    @Override
    public void start() {
        gateAcceptor.start();
        zoneAcceptor.start();
        managerAcceptor.start();
        relationAcceptor.start();
    }

    @Override
    public void stop() {
        gateAcceptor.stop();
        zoneAcceptor.stop();
        managerAcceptor.stop();
        relationAcceptor.stop();
    }

    @Override
    public void update(long dt) {
        GateServerSessionMng.getInstance().update(dt);
        ZoneServerSessionMng.getInstance().update(dt);
        ManagerServerSessionMng.getInstance().update(dt);
        RelationServerSessionMng.getInstance().update(dt);
    }

    @Override
    public void connect(ServerTag tag, String ip, int port) {
        log.warn("global acceptor don't need to connect to other acceptor,it's just listen");
    }

    private GateAcceptor createGateServer() {
        try {
            gateAcceptor = new GateAcceptor();
            return gateAcceptor;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ManagerAcceptor createManagerServer() {
        try {
            managerAcceptor = new ManagerAcceptor();
            return managerAcceptor;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ZoneAcceptor createZoneServer() {
        try {
            zoneAcceptor = new ZoneAcceptor();
            return zoneAcceptor;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private RelationAcceptor createRelationServer() {
        try {
            relationAcceptor = new RelationAcceptor();
            return relationAcceptor;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    void sendConnectionCommand(ServerTag tag) {
        switch (tag.getType()) {
            case Gate:
                doGateRegister(tag);
                break;
            case Manager:
                doManagerRegister(tag);
                break;
            case Zone:
                doZoneRegister(tag);
                break;
            case Relation:
                doRelationRegister(tag);
                break;
        }
    }

    /**
     * manager注册
     *
     * @param managerTag server标签
     */

    private void doManagerRegister(ServerTag managerTag) {
        //send this manager connection info to zones
        broadcastAcceptorInfo2Connectors(managerTag, ServerType.Zone);
        //send this manager connection info to gates
        broadcastAcceptorInfo2Connectors(managerTag, ServerType.Gate);
        //send this manager connection info to relations
        broadcastAcceptorInfo2Connectors(managerTag, ServerType.Relation);

//        //send this manager connection info to other managers
//        broadcastAcceptorInfo2Connectors(managerTag, ServerType.Manager);
//        //send other managers connection info to this manager
//        for (AbstractSession manager : ManagerServerSessionMng.getInstance().getRegisterSessions().values()) {
//            ServerTag tag = (ServerTag) manager.getTag();
//            if (tag.getGroupId() < managerTag.getGroupId()) {  //约定 groupId 大的为连接方 小的为监听方
//                sendAcceptorInfo2Connector(tag, managerTag);
//            }
//        }
    }

    /***
     * zone注册
     * @param zoneTag server标签
     */
    private void doZoneRegister(ServerTag zoneTag) {

        //send managers connection info to this zone
        for (Map.Entry<ISessionTag,AbstractSession> entry  : ManagerServerSessionMng.getInstance().getRegisterSessions().entrySet()) {
            ServerTag tag = (ServerTag) entry.getKey();
            if (zoneTag.getGroupId() == tag.getGroupId()){
                sendAcceptorInfo2Connector(tag, zoneTag);
            }
        }

        //send relation connection info to this zone
        for (Map.Entry<ISessionTag,AbstractSession> entry : RelationServerSessionMng.getInstance().getRegisterSessions().entrySet()) {
            ServerTag tag = (ServerTag) entry.getKey();
            if (zoneTag.getGroupId() == tag.getGroupId()) {
                sendAcceptorInfo2Connector(tag, zoneTag);
            }
        }

        //send this zone connection info to gates
        broadcastAcceptorInfo2Connectors(zoneTag, ServerType.Gate);
    }

    /***
     * gate注册
     * @param gateTag server标签
     */
    private void doGateRegister(ServerTag gateTag) {
        //send barracks connection info to this gate
//        for (AbstractSession barrack : BarrackServerSessionMng.getInstance().getRegisterSessions().values()) {
//            Data serverData = dataList.getData(barrack.getKey());
//            if (serverData != null) {
//                sendConnectionInfo2Server(gateTag, serverData);
//            }
//        }

        //send managers connection info to this gate
        for (Map.Entry<ISessionTag,AbstractSession> entry : ManagerServerSessionMng.getInstance().getRegisterSessions().entrySet()) {
            ServerTag tag = (ServerTag) entry.getKey();
            if (tag.getGroupId()== gateTag.getGroupId()){
                sendAcceptorInfo2Connector(tag, gateTag);
            }
        }

        //send zones connection info to this gate
        for (Map.Entry<ISessionTag,AbstractSession> entry : ZoneServerSessionMng.getInstance().getRegisterSessions().entrySet()) {
            ServerTag tag = (ServerTag) entry.getKey();
            if (tag.getGroupId()== gateTag.getGroupId()) {
                sendAcceptorInfo2Connector(tag, gateTag);
            }
        }
    }

    /**
     * relation 注册
     *
     * @param relationTag server标签
     */
    private void doRelationRegister(ServerTag relationTag) {
        //send other manager's connection info to this relation
        for (Map.Entry<ISessionTag,AbstractSession> entry : ManagerServerSessionMng.getInstance().getRegisterSessions().entrySet()) {
            ServerTag tag = (ServerTag) entry.getKey();
            if (relationTag.getGroupId()==tag.getGroupId()){
                sendAcceptorInfo2Connector(tag, relationTag);
            }
        }

        //send this relation connection info to zones
        broadcastAcceptorInfo2Connectors(relationTag, ServerType.Zone);

        //send this relation connection info to other relations
        broadcastAcceptorInfo2Connectors(relationTag, ServerType.Relation);
        //send other relation's connection info to this relation
        for (Map.Entry<ISessionTag,AbstractSession> entry : RelationServerSessionMng.getInstance().getRegisterSessions().entrySet()) {
            ServerTag tag = (ServerTag) entry.getKey();
            if (tag.getGroupId() < relationTag.getGroupId()) {  //约定 groupId 大的为连接方 小的为监听方
                sendAcceptorInfo2Connector(tag, relationTag);
            }
        }
    }

    /**
     * 发送acceptorTag连接信息到connector
     *
     * @param acceptorTag  连接监听方
     * @param connectorTag 连接发起方
     */
    private void sendAcceptorInfo2Connector(ServerTag acceptorTag, ServerTag connectorTag) {
        DataList dataList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serverData = dataList.getData(acceptorTag.toString());
        String ip = serverData.getString("ip");
        int listenPort = getListenPortFromData(connectorTag.getType(), serverData);

        ServerRegister.Server_Tag.Builder tagBuilder = getServerTag(acceptorTag.getType(), acceptorTag.getGroupId(), acceptorTag.getSubId());
        ServerRegister.Connect_Info.Builder connectInfoBuilder = getServerConnectInfo(ip, listenPort);
        ServerRegister.MSG_Server_Connect_Command.Builder commandBuilder = getConnectionCommand(tagBuilder, connectInfoBuilder);

        switch (connectorTag.getType()) {
            case Manager:
                ManagerServerSessionMng.getInstance().send2Session(connectorTag, commandBuilder.build());
                break;
            case Relation:
                RelationServerSessionMng.getInstance().send2Session(connectorTag, commandBuilder.build());
                break;
            case Zone:
                ZoneServerSessionMng.getInstance().send2Session(connectorTag, commandBuilder.build());
                break;
            case Gate:
                GateServerSessionMng.getInstance().send2Session(connectorTag, commandBuilder.build());
                break;
            case Default:
                log.error("send connection info to connector {} got an error !", connectorTag.toString());
                break;
        }
    }

    /**
     * 广播acceptorTag信息到connectors
     *
     * @param acceptorTag   监听方
     * @param connectorType 连接方
     */
    private void broadcastAcceptorInfo2Connectors(ServerTag acceptorTag, ServerType connectorType) {
        DataList dataList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serverData = dataList.getData(acceptorTag.toString());
        String ip = serverData.getString("ip");
        int listenPort = getListenPortFromData(connectorType, serverData);

        ServerRegister.Server_Tag.Builder tagBuilder = getServerTag(acceptorTag.getType(), acceptorTag.getGroupId(), acceptorTag.getSubId());
        ServerRegister.Connect_Info.Builder infoBuilder = getServerConnectInfo(ip, listenPort);
        ServerRegister.MSG_Server_Connect_Command.Builder commandBuilder = getConnectionCommand(tagBuilder, infoBuilder);

        switch (connectorType) {
            case Manager:
                if (connectorType == acceptorTag.getType()) {
                    ManagerServerSessionMng.getInstance().broadcastAllExceptServer(commandBuilder.build(), acceptorTag);
                } else {
                    ManagerServerSessionMng.getInstance().broadcastAll(commandBuilder.build());
                }
                break;
            case Relation:
                if (connectorType == acceptorTag.getType()) {
                    RelationServerSessionMng.getInstance().broadcastByGroupExceptServer(commandBuilder.build(), acceptorTag.getGroupId(), acceptorTag);
                } else {
                    RelationServerSessionMng.getInstance().broadcastByGroup(commandBuilder.build(), acceptorTag.getGroupId());
                }
                break;
            case Zone:
                if (connectorType == acceptorTag.getType()) {
                    ZoneServerSessionMng.getInstance().broadcastByGroupExceptServer(commandBuilder.build(), acceptorTag.getGroupId(), acceptorTag);
                } else {
                    ZoneServerSessionMng.getInstance().broadcastByGroup(commandBuilder.build(), acceptorTag.getGroupId());
                }
                break;
            case Gate:
                if (connectorType == acceptorTag.getType()) {
                    GateServerSessionMng.getInstance().broadcastAllExceptServer(commandBuilder.build(), acceptorTag);
                } else {
                    GateServerSessionMng.getInstance().broadcastAll(commandBuilder.build());
                }
                break;
            case Default:
                log.error("broadcast connection info to {} got an error !", acceptorTag.toString());
                break;
        }
    }

    /**
     * 读取监听端口
     *
     * @param type        监听端口server类型
     * @param serviceData 数据
     * @return 监听端口
     */
    private int getListenPortFromData(ServerType type, Data serviceData) {
        int listenPort = 0;
        switch (type) {
            case Manager:
                listenPort = serviceData.getInteger("managerPort");
                break;
            case Relation:
                listenPort = serviceData.getInteger("relationPort");
                break;
            case Zone:
                listenPort = serviceData.getInteger("zonePort");
                break;
            case Gate:
                listenPort = serviceData.getInteger("gatePort");
                break;
            case Default:
                log.error("{} get listen port got an error type!", serviceData.getName());
                break;
        }
        return listenPort;
    }

}

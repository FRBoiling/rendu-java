package manager.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.IConnectManager;
import lombok.extern.slf4j.Slf4j;
import manager.Context;
import manager.gate.GateAcceptor;
import manager.gate.GateServerSessionMng;
import manager.global.GlobalConnector;
import manager.global.GlobalServerSessionMng;
import manager.manager.ManagerAcceptor;
import manager.manager.ManagerServerSessionMng;
import manager.manager.ManagerConnector;
import manager.relation.RelationAcceptor;
import manager.relation.RelationServerSessionMng;
import manager.zone.ZoneAcceptor;
import manager.zone.ZoneServerSessionMng;
import protocol.server.register.ServerRegister;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-07
 * Time: 15:48
 **/

@Slf4j
public class ConnectManager implements IConnectManager {

    private GlobalConnector globalConnector;

    private ZoneAcceptor zoneAcceptor;
    private RelationAcceptor relationAcceptor;
    private GateAcceptor gateAcceptor;

    private ManagerAcceptor managerAcceptor;

    private HashMap<ServerTag, ManagerConnector> managerConnectorHashMap;

    private GlobalConnector createGlobalAcceptor() {
        return new GlobalConnector();
    }

    private ZoneAcceptor createZoneAcceptor() {
        return new ZoneAcceptor();
    }

    private RelationAcceptor createRelationAcceptor() {
        return new RelationAcceptor();
    }

    private GateAcceptor createGateAcceptor() {
        return new GateAcceptor();
    }

    private ManagerAcceptor createManagerAcceptor() {
        return new ManagerAcceptor();
    }

    private ManagerConnector createManagerConnector() {
        return new ManagerConnector();
    }

    @Override
    public void init() {

        globalConnector = createGlobalAcceptor();
        globalConnector.init(null);

        zoneAcceptor = createZoneAcceptor();
        zoneAcceptor.init(null);

        relationAcceptor = createRelationAcceptor();
        relationAcceptor.init(null);

        gateAcceptor = createGateAcceptor();
        gateAcceptor.init(null);

        managerAcceptor = createManagerAcceptor();
        managerAcceptor.init(null);

        managerConnectorHashMap = new HashMap<>();
    }

    @Override
    public void start() {
        globalConnector.start();

        zoneAcceptor.start();
        gateAcceptor.start();
        relationAcceptor.start();

        managerAcceptor.start();
    }

    @Override
    public void stop() {
        globalConnector.stop();

        gateAcceptor.stop();
        zoneAcceptor.stop();
        relationAcceptor.stop();

        managerAcceptor.stop();

        for (ManagerConnector manager : managerConnectorHashMap.values()) {
            manager.stop();
        }
    }

    @Override
    public void update(long dt) {
        GlobalServerSessionMng.getInstance().update(dt);
        ManagerServerSessionMng.getInstance().update(dt);
        ZoneServerSessionMng.getInstance().update(dt);
        GateServerSessionMng.getInstance().update(dt);
        RelationServerSessionMng.getInstance().update(dt);
    }

    @Override
    public void connect(ServerTag tag, String ip, int port) {
        switch (tag.getType()) {
            case Manager:
                if (Context.tag.getSubId() > tag.getSubId())  //约定规则：subId大的连接subId小的。
                {
                    AbstractSession managerSession = ManagerServerSessionMng.getInstance().getRegisterSession(tag);
                    if (managerSession == null) {
                        connectManagerServer(ip, port, tag);
                    } else {
                        log.error("already connected an manager session {}!", managerSession.getTag().toString());
                    }
                }
                break;
            case Default:
                break;
        }
    }

    private void connectManagerServer(String ip, int port, ServerTag tag) {
        ManagerConnector managerConnector = managerConnectorHashMap.get(tag);
        if (managerConnector == null) {
            managerConnector = createManagerConnector();
            managerConnector.init(null);
            managerConnectorHashMap.put(tag, managerConnector);
        }
        if (managerConnector.isOpened()) {
            log.error("{} already started an manager server!", Context.tag.toString());
        } else {
            managerConnector.connect(ip, port);
            managerConnector.start();
        }
    }
}

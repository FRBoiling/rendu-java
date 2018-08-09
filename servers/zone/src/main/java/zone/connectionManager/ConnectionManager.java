package zone.connectionManager;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.serviceframe.IConnectionManager;
import lombok.extern.slf4j.Slf4j;
import zone.Context;
import zone.gate.GateAcceptor;
import zone.gate.GateServerSessionMng;
import zone.global.GlobalConnector;
import zone.global.GlobalServerSessionMng;
import zone.manager.ManagerConnector;
import zone.manager.ManagerServerSessionMng;
import zone.relation.RelationConnector;
import zone.relation.RelationServerSessionMng;

import java.util.HashMap;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-07
 * Time: 15:48
 **/
@Slf4j
public class ConnectionManager implements IConnectionManager {
    private GlobalConnector globalConnector;

    private GateAcceptor gateAcceptor;

    private HashMap<ServerTag, ManagerConnector> managerConnectorHashMap;
    private HashMap<ServerTag, RelationConnector> relationConnectorHashMap;

    private GlobalConnector createGlobalConnector() {
        try {
            return new GlobalConnector();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private GateAcceptor createGateServer() {
        try {
            return new GateAcceptor();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ManagerConnector createManagerConnector() {
        try {
            return new ManagerConnector();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private RelationConnector createRelationConnector() {
        try {
            return new RelationConnector();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        globalConnector = createGlobalConnector();
        globalConnector.init(null);

        gateAcceptor = createGateServer();
        gateAcceptor.init(null);

        managerConnectorHashMap = new HashMap<>();
        relationConnectorHashMap = new HashMap<>();
    }

    @Override
    public void start() {
        globalConnector.start();
        gateAcceptor.start();
    }

    @Override
    public void stop() {
        globalConnector.stop();
        gateAcceptor.stop();
        for (ManagerConnector manager : managerConnectorHashMap.values()) {
            manager.stop();
        }
        for (RelationConnector relation : relationConnectorHashMap.values()) {
            relation.stop();
        }
    }

    @Override
    public void update(long dt) {
        GlobalServerSessionMng.getInstance().update(dt);
        ManagerServerSessionMng.getInstance().update(dt);
        GateServerSessionMng.getInstance().update(dt);
        RelationServerSessionMng.getInstance().update(dt);
    }

    @Override
    public void connect(ServerTag tag, String ip, int port) {
        switch (tag.getType()) {
            case Manager:
                AbstractSession session = ManagerServerSessionMng.getInstance().getRegisterSession(tag);
                if (session == null) {
                    connectManagerServer(ip, port, tag);
                } else {
                    log.error("already connected an manager session {}!", session.getTag().toString());
                }
                break;
            case Relation:
                AbstractSession relationSession = RelationServerSessionMng.getInstance().getRegisterSession(tag);
                if (relationSession == null) {
                    connectRelationServer(ip, port, tag);
                } else {
                    log.error("already connected an relation session {}!", relationSession.getTag().toString());
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
            log.error("{} already started an manager acceptor!", Context.tag.toString());
        } else {
            managerConnector.connect(ip, port);
            managerConnector.start();
        }
    }

    private void connectRelationServer(String ip, int port, ServerTag tag) {
        RelationConnector relationConnector = relationConnectorHashMap.get(tag);
        if (relationConnector == null) {
            relationConnector = createRelationConnector();
            relationConnector.init(null);
            relationConnectorHashMap.put(tag, relationConnector);
        }
        if (relationConnector.isOpened()) {
            log.error("{} already started an manager acceptor!", Context.tag);
        } else {
            relationConnector.connect(ip, port);
            relationConnector.start();
        }
    }
}

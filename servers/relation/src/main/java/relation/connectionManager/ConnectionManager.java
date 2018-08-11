package relation.connectionManager;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.serviceframe.IConnectionManager;
import lombok.extern.slf4j.Slf4j;
import relation.Context;
import relation.global.GlobalConnector;
import relation.global.GlobalServerSessionMng;
import relation.manager.ManagerConnector;
import relation.manager.ManagerServerSessionMng;
import relation.relation.RelationAcceptor;
import relation.relation.RelationConnector;
import relation.relation.RelationServerSessionMng;
import relation.zone.ZoneAcceptor;
import relation.zone.ZoneServerSessionMng;

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

    private ZoneAcceptor zoneAcceptor;
    private RelationAcceptor relationAcceptor;

    private HashMap<ServerTag, ManagerConnector> managerConnectorHashMap;
    private HashMap<ServerTag, RelationConnector> relationConnectorHashMap;

    private GlobalConnector createGlobalConnector() {
        return new GlobalConnector();
    }

    private ZoneAcceptor createZoneAcceptor() {
        return new ZoneAcceptor();
    }

    private RelationAcceptor createRelationAcceptor() {
        return new RelationAcceptor();
    }

    private RelationConnector createRelationConnector() {
        return new RelationConnector();
    }

    private ManagerConnector createManagerConnector() {
        return new ManagerConnector();
    }

    @Override
    public void init() {
        globalConnector = createGlobalConnector();
        globalConnector.init(null);

        zoneAcceptor = createZoneAcceptor();
        zoneAcceptor.init(null);

        relationAcceptor = createRelationAcceptor();
        relationAcceptor.init(null);

        managerConnectorHashMap = new HashMap<>();
        relationConnectorHashMap = new HashMap<>();
    }

    @Override
    public void start() {
        globalConnector.start();
        zoneAcceptor.start();
        relationAcceptor.start();
    }

    @Override
    public void stop() {
        globalConnector.stop();
        zoneAcceptor.stop();
        relationAcceptor.stop();
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
        ZoneServerSessionMng.getInstance().update(dt);
        RelationServerSessionMng.getInstance().update(dt);
    }

    @Override
    public void connect(ServerTag tag, String ip, int port) {
        if (tag.getGroupId() == Context.tag.getGroupId()) {

            switch (tag.getType()) {
                case Manager:
                    AbstractSession managerSession = ManagerServerSessionMng.getInstance().getRegisterSession(tag);
                    if (managerSession == null) {
                        connectManagerServer(ip, port, tag);
                    } else {
                        log.error("already connected an manager session {}!", managerSession.getTag().toString());
                    }
                    break;
                case Relation:
                    if (Context.tag.getSubId() > tag.getSubId()) { //约定规则：subId大的连接subId小的。
                        AbstractSession relationSession = RelationServerSessionMng.getInstance().getRegisterSession(tag);
                        if (relationSession == null) {
                            connectRelationServer(ip, port, tag);
                        } else {
                            log.error("already connected an relation session {}!", relationSession.getTag().toString());
                        }
                    }
                    break;
                case Default:
                    break;
            }
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
            log.error("{} already started an manager acceptor!", Context.tag.toString());
        } else {
            relationConnector.connect(ip, port);
            relationConnector.start();
        }
    }

}

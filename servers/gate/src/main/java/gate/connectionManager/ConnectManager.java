package gate.connectionManager;

import com.google.protobuf.InvalidProtocolBufferException;
import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.IConnectManager;
import core.network.codec.Packet;
import gate.Context;
import gate.client.ClientAcceptor;
import gate.client.ClientSessionMng;
import gate.global.GlobalConnector;
import gate.global.GlobalServerSessionMng;
import gate.manager.ManagerConnector;
import gate.manager.ManagerServerSessionMng;
import gate.zone.ZoneConnector;
import gate.zone.ZoneServerSessionMng;
import lombok.extern.slf4j.Slf4j;
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
    private ClientAcceptor clientAcceptor;

    private HashMap<ServerTag, ManagerConnector> managerConnectorHashMap;
    private HashMap<ServerTag, ZoneConnector> zoneConnectorHashMap;

    private GlobalConnector createGlobalConnector() {
        try {
            return new GlobalConnector();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ZoneConnector createZoneConnector() {
        try {
            return new ZoneConnector();
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

    private ClientAcceptor createClientAcceptor() {
        try {
            return new ClientAcceptor();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() {
        globalConnector = createGlobalConnector();
        globalConnector.init(null);

        clientAcceptor = createClientAcceptor();
        clientAcceptor.init(null);

        managerConnectorHashMap = new HashMap<>();
        zoneConnectorHashMap = new HashMap<>();
    }

    @Override
    public void start() {
        globalConnector.start();
        clientAcceptor.start();
    }

    @Override
    public void stop() {
        globalConnector.stop();
        clientAcceptor.stop();

        for (ManagerConnector manager : managerConnectorHashMap.values()) {
            manager.stop();
        }
        for (ZoneConnector zone : zoneConnectorHashMap.values()) {
            zone.stop();
        }

    }

    @Override
    public void update(long dt) {
        GlobalServerSessionMng.getInstance().update(dt);
        ClientSessionMng.getInstance().update(dt);
        ManagerServerSessionMng.getInstance().update(dt);
        ZoneServerSessionMng.getInstance().update(dt);
    }

    @Override
    public void connect(ServerTag tag, String ip, int port) {
        switch (tag.getType()) {
            case Manager:
                AbstractSession managerSession = ManagerServerSessionMng.getInstance().getRegisterSession(tag);
                if (managerSession == null) {
                    connectManagerServer(ip, port, tag);
                } else {
                    log.error("already connected an manager session {}!", managerSession.toString());
                }
                break;
            case Zone:
                AbstractSession zoneSession = ZoneServerSessionMng.getInstance().getRegisterSession(tag);
                if (zoneSession == null) {
                    connectZoneServer(ip, port, tag);
                } else {
                    log.error("already connected an zone session {}!", zoneSession.toString());
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
            log.error("{} already started an manager server :{} !", Context.tag.toString(), tag.toString());
        } else {
            managerConnector.connect(ip, port);
            managerConnector.start();
        }
    }

    private void connectZoneServer(String ip, int port, ServerTag tag) {
        ZoneConnector zoneConnector = zoneConnectorHashMap.get(tag);
        if (zoneConnector == null) {
            zoneConnector = createZoneConnector();
            zoneConnector.init(null);
            zoneConnectorHashMap.put(tag, zoneConnector);
        }
        if (zoneConnector.isOpened()) {
            log.error("{} already started an zone server :{} !", Context.tag.toString(), tag.toString());
        } else {
            zoneConnector.connect(ip, port);
            zoneConnector.start();
        }
    }

}

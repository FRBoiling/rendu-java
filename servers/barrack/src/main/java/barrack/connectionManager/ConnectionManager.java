package barrack.connectionManager;

import barrack.global.GlobalConnector;
import core.base.model.ServerTag;
import core.base.serviceframe.IConnectionManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-13
 * Time: 09:59
 */

@Slf4j
public class ConnectionManager implements IConnectionManager {
    private GlobalConnector globalConnector;
//    private ZoneServer zoneServer;

    public GlobalConnector createGlobalServer() {
        try {
            return new GlobalConnector();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

//    public ZoneServer createZoneServer() {
//        try {
//            return new ZoneServer();
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void init() {
        globalConnector =createGlobalServer();
//        zoneServer =createZoneServer();
    }

    @Override
    public void start() {
        globalConnector.start();
//        zoneServer.start();
    }

    @Override
    public void stop() {
        globalConnector.stop();
//        zoneServer.stop();
}

    @Override
    public void update(long dt) {
        globalConnector.update(dt);
//        zoneServer.update();
    }

    @Override
    public void connect(ServerTag tag, String ip, int port) {

    }

}

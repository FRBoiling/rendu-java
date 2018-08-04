package barrack.connectionManager;

import barrack.global.GlobalServer;
import core.base.model.ServerTag;
import core.base.serviceframe.IConnectManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-13
 * Time: 09:59
 */

@Slf4j
public class ConnectManager implements IConnectManager {
    private GlobalServer globalServer;
//    private ZoneServer zoneServer;

    public GlobalServer createGlobalServer() {
        try {
            return new GlobalServer();
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
        globalServer =createGlobalServer();
//        zoneServer =createZoneServer();
    }

    @Override
    public void start() {
        globalServer.start();
//        zoneServer.start();
    }

    @Override
    public void stop() {
        globalServer.stop();
//        zoneServer.stop();
}

    @Override
    public void update(long dt) {
        globalServer.update(dt);
//        zoneServer.update();
    }

    @Override
    public void connect(ServerTag tag, String ip, int port) {

    }

}

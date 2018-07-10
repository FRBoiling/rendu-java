package relation.connectionManager;

import core.base.serviceframe.IConnectManager;
import lombok.extern.slf4j.Slf4j;
import relation.global.GlobalServer;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Administrator
 * Date: 2018-07-07
 * Time: 15:48
 **/

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
    public void update() {
        globalServer.update();
//        zoneServer.update();
    }

}

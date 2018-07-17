package gate.connectionManager;

import core.base.serviceframe.IConnectManager;
import gate.client.Client;
import gate.global.GlobalServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-07
 * Time: 15:48
 **/

@Slf4j
public class ConnectManager implements IConnectManager {
    private GlobalServer globalServer;
    private Client client;
//    private ZoneServer zoneServer;

    public GlobalServer createGlobalServer() {
        try {
            return new GlobalServer();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Client createClient() {
        try {
            return new Client();
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
        client = createClient();
//        zoneServer =createZoneServer();
    }

    @Override
    public void start() {
        globalServer.start();
        client.start();
//        zoneServer.start();
    }

    @Override
    public void stop() {
        globalServer.stop();
        client.stop();
//        zoneServer.stop();
}

    @Override
    public void update() {
        globalServer.update();
        client.update();
//        zoneServer.update();
    }

}

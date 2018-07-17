package zone.connectionManager;

import core.base.common.AbstractSession;
import core.base.model.ServerTag;
import core.base.serviceframe.IConnectManager;
import lombok.extern.slf4j.Slf4j;
import zone.ZoneServiceContext;
import zone.global.GlobalServer;
import zone.manager.ManagerServer;
import zone.manager.ManagerServerSessionMng;

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
    private ManagerServer managerServer;

    private GlobalServer createGlobalServer() {
        try {
            return new GlobalServer();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ManagerServer createManagerServer() {
        try {
            return new ManagerServer();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void connectManagerServer(String ip,int port){
        if ( managerServer !=null && managerServer.isOpened()){
            log.error("{} already started an managerServer server!",ZoneServiceContext.tag.getKey());
        } else {
            managerServer =createManagerServer();
            managerServer.connect(ip,port);
            managerServer.start();
        }
    }

    public void connect(ServerTag tag,String ip,int port){
        switch ( tag.getType()){
            case Manager:
                AbstractSession session = ManagerServerSessionMng.getInstance().getRegisterSession(tag.getKey());
                if (session == null)
                {
                    connectManagerServer(ip,port);
                }
                else {
                    log.error("zone already connected an manager session {}!",session.getKey());
                }
                break;
        }
    }

    @Override
    public void init() {
        globalServer = createGlobalServer();
    }

    @Override
    public void start() {
        globalServer.start();
    }

    @Override
    public void stop() {
        globalServer.stop();
    }

    public void update(){
        globalServer.update();
        if (managerServer !=null&& managerServer.isOpened()){
            managerServer.update();
        }
    }


}

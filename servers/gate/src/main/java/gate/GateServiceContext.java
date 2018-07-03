package gate;

import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.DriverRunnable;
import core.base.serviceframe.IService;
import core.network.ServiceState;
import gate.global.GlobalServer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:59
 */

public class GateServiceContext implements IService {
    public static ServerTag tag;
    private GlobalServer globalServer;

    public void initServers(){
        globalServer = createGlobalServer();
        globalServer.start();
    }

    private GlobalServer createGlobalServer() {
        try {
            globalServer = new GlobalServer();
            return globalServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(String[] args) {
        ServerType serverType = ServerType.Gate;
        Object [] tagParam = new Object[]{
                serverType,
                0,
                0
        };
        if (args.length>=2){
            Integer groupId = Integer.parseInt(args[0]);
            Integer subId = Integer.parseInt(args[1]);
            tag = new ServerTag();
            tagParam[1] = groupId;
            tagParam[2] = subId;
        }
        tag.initTag(tagParam);

        initServers();
    }

    @Override
    public void start() {
        DriverRunnable mainThread = new DriverRunnable( "MainThread_Gate",this);
        mainThread.run();
    }

    @Override
    public void update() {

    }

    @Override
    public void stop() {

    }

    @Override
    public ServiceState getState() {
        return null;
    }


}

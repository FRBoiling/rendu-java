package global;

import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.DriverThread;
import core.base.serviceframe.IService;
import core.network.ServiceState;
import global.gate.GateServer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 11:04
 */

public class GlobalServiceContext implements IService {
    public static ServerTag tag;
    DriverThread mainThread;

    private GateServer gateServer;

    public GateServer createGateServer() {
        try {
            gateServer = new GateServer();
            return gateServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void initServers(){
        gateServer = createGateServer();
        gateServer.start();
    }

    @Override
    public void init(String[] args){
        ServerType serverType = ServerType.Global;
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
        mainThread = new DriverThread( "MainThread_Global",this);
        mainThread.start();
    }

    @Override
    public void stop() {

    }

    @Override
    public void update() {
        while (true){
            try {
                Thread.sleep(100);
                gateServer.update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ServiceState getState() {
        return null;
    }

}

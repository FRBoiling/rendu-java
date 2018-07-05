package gate;

import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.DriverThread;
import core.base.serviceframe.IService;
import core.network.ServiceState;
import gate.global.GlobalServer;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:59
 */

public class GateServiceContext implements IService {
    public static ServerTag tag;
    DriverThread mainThread;

    private GlobalServer globalServer;

    public void initPath(){
       // PathManager
    }

    public void  initData(){


    }

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
        tag = new ServerTag();
        if (args.length>=2){
            Integer groupId = Integer.parseInt(args[0]);
            Integer subId = Integer.parseInt(args[1]);
            tag.setTag(serverType,groupId,subId);
        }
        initData();
        initServers();
    }

    @Override
    public void start() {
        mainThread= new DriverThread( "MainThread_Gate",this);
        mainThread.start();
    }

    @Override
    public void stop() {

    }

    @Override
    public void update() {
        while (true){
            try {
                globalServer.update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public ServiceState getState() {
        return null;
    }


}

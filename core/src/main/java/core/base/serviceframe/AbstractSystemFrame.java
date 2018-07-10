package core.base.serviceframe;

import core.base.model.ServerTag;
import core.network.ServiceState;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Administrator
 * Date: 2018-07-09
 * Time: 13:52
 **/
public abstract class AbstractSystemFrame implements IService,ISystemFrame{
    public ServiceState state = ServiceState.STOPPED;
    public static ServerTag tag;

    private DriverThread driverThread;
    IConnectManager connectManager;

    public void initConnectManager(IConnectManager connectManager)
    {
        this.connectManager = connectManager;
        initServers();
    }

    public void initMainThread(String name){
        driverThread= new DriverThread( name,this);
    }

    @Override
    public void init(String[] args) {
        initPath();
        initLogger();
        initXmlData();
        initLibData();
        initOpenServerTime();
        initDB();
        initRedis();
        intiProtocol();
    }

    @Override
    public void start() {
        state = ServiceState.RUNNING;
        driverThread.start();
        connectManager.start();
    }

    @Override
    public void stop() {
        state = ServiceState.STOPPED;
        connectManager.stop();
    }

    @Override
    public void update() {
        while (isOpened()){
            try {
                connectManager.update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ServiceState getState() {
        return state;
    }

    @Override
    public void initPath() {
    }

    @Override
    public void initLibData() {

    }

    @Override
    public void initXmlData() {

    }

    @Override
    public void initLogger() {

    }

    @Override
    public void initDB() {

    }

    @Override
    public void initRedis() {

    }

    @Override
    public void initOpenServerTime() {

    }

    @Override
    public void initServers() {
        connectManager.init();
    }

    @Override
    public void updateXml() {

    }
}

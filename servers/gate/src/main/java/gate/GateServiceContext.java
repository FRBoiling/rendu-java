package gate;

import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.DriverThread;
import core.base.serviceframe.IService;
import core.base.serviceframe.ISystemFrame;
import core.network.ServiceState;
import gate.global.GlobalServer;
import pathExt.PathManager;
import protocol.gate.global.G2GMIdGenerater;
import protocol.global.gate.GM2GIdGenerater;
import protocol.server.register.ServerRegisterIdGenerater;
import util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:59
 */

public class GateServiceContext implements IService,ISystemFrame {
    public ServiceState state = ServiceState.STOPPED;
    public static ServerTag tag;
    DriverThread mainThread;
    private GlobalServer globalServer;

    @Override
    public void init(String[] args) {
        ServerType serverType = ServerType.Gate;
        tag = new ServerTag();
        if (args.length>=1){
            Integer subId = Integer.parseInt(args[0]);
            tag.setTag(serverType,0,subId);
        }
        mainThread= new DriverThread( "GateDriverThread",this);
        initPath();
        initLogger();
        initXmlData();
        initLibData();
        initOpenServerTime();
        initDB();
        initRedis();
        intiProtocol();
        initServers();
    }

    @Override
    public void start() {
        state = ServiceState.RUNNING;
        mainThread.start();
        globalServer.start();
    }

    @Override
    public void stop() {
        state = ServiceState.STOPPED;
    }

    @Override
    public void update() {
        while (isOpened()){
            try {
                globalServer.update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ServiceState getState() {
        return state;
    }

    private GlobalServer createGlobalServer() {
        try {
            return new GlobalServer();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initServers(){
        globalServer = createGlobalServer();
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        GM2GIdGenerater.GenerateId();
        G2GMIdGenerater.GenerateId();
    }

    @Override
    public void initPath() {
        PathManager.getInstance().initPath();
    }

    @Override
    public void initLibData() {

    }

    @Override
    public void initXmlData() {
        List<File> fileList = new ArrayList<File>();
        FileUtil.findFiles(PathManager.getInstance().getXmlPath(),"*xml",fileList);
        for (Object obj :fileList){
            File f = (File) obj;
//            System.out.println("-----"+f.toString());
            DataListManager.getInstance().Parse(f.toString());
        }
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
    public void updateXml() {

    }
}

package global;

import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.DriverThread;
import core.base.serviceframe.IService;
import core.base.serviceframe.ISystemFrame;
import core.network.ServiceState;
import global.gate.GateServer;
import global.manager.ManagerServer;
import global.relation.RelationServer;
import global.zone.ZoneServer;
import pathExt.PathManager;
import protocol.gate.global.G2GMIdGenerater;
import protocol.global.gate.GM2GIdGenerater;
import protocol.global.manager.GM2MIdGenerater;
import protocol.global.relation.GM2RIdGenerater;
import protocol.global.zone.GM2ZIdGenerater;
import protocol.manager.global.M2GMIdGenerater;
import protocol.relation.global.R2GMIdGenerater;
import protocol.server.register.ServerRegisterIdGenerater;
import protocol.zone.global.Z2GMIdGenerater;
import util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 11:04
 */

public class GlobalServiceContext implements IService,ISystemFrame {
    public ServiceState state = ServiceState.STOPPED;
    public static ServerTag tag;
    DriverThread mainThread;

    private GateServer gateServer;
    private ManagerServer managerServer;
    private ZoneServer zoneServer;
    private RelationServer relationServer;

    @Override
    public void init(String[] args){
        ServerType serverType = ServerType.Global;
        tag = new ServerTag();
        tag.setTag(serverType,0,0);

        mainThread = new DriverThread( "GlobalDriverThread",this);
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
        zoneServer.start();
        relationServer.start();
        gateServer.start();
        managerServer.start();
    }

    @Override
    public void stop() {
       state = ServiceState.STOPPED;
    }

    @Override
    public void update() {
        while (isOpened()){
            try {
                Thread.sleep(25);
                zoneServer.update();
                relationServer.update();
                gateServer.update();
                managerServer.update();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ServiceState getState() {
        return state;
    }

    public GateServer createGateServer() {
        try {
            gateServer = new GateServer();
            return gateServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public ManagerServer createManagerServer() {
        try {
            managerServer = new ManagerServer();
            return managerServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public ZoneServer createZoneServer() {
        try {
            zoneServer = new ZoneServer();
            return zoneServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public RelationServer createRelationServer() {
        try {
            relationServer = new RelationServer();
            return relationServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initServers(){
        gateServer = createGateServer();
        managerServer = createManagerServer();
        zoneServer = createZoneServer();
        relationServer = createRelationServer();
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        G2GMIdGenerater.GenerateId();
        GM2GIdGenerater.GenerateId();
        Z2GMIdGenerater.GenerateId();
        GM2ZIdGenerater.GenerateId();
        R2GMIdGenerater.GenerateId();
        GM2RIdGenerater.GenerateId();
        M2GMIdGenerater.GenerateId();
        GM2MIdGenerater.GenerateId();
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

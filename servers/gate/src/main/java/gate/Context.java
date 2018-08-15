package gate;

import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.AbstractServiceFrame;
import core.base.serviceframe.DBDriverThread;
import gamedb.DBManager;
import gamedb.dao.AbstractDBOperator;
import gate.client.AuthorizationMng;
import gate.client.ClientSessionMng;
import gate.connectionManager.ConnectionManager;
import gate.global.GlobalServerSessionMng;
import gate.manager.ManagerServerResponseMng;
import gate.manager.ManagerServerSessionMng;
import gate.zone.ZoneServerSessionMng;
import pathExt.PathManager;
import protocol.client.Client;
import protocol.client.ClientIdGenerater;
import protocol.gate.global.G2GMIdGenerater;
import protocol.gate.manager.G2MIdGenerater;
import protocol.gate.zone.G2ZIdGenerater;
import protocol.global.gate.GM2GIdGenerater;
import protocol.manager.gate.M2GIdGenerater;
import protocol.server.register.ServerRegisterIdGenerater;
import protocol.zone.gate.Z2GIdGenerater;
import util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:59
 */

public class Context extends AbstractServiceFrame {
    public static ConnectionManager connectManager;
    public DBManager db;
    private DBDriverThread dbThread;

    @Override
    public void init(String[] args) {
        super.init(args);

        ServerType serverType = ServerType.Gate;
        tag = new ServerTag();
        if (args.length >= 2) {
            Integer areaId = Integer.parseInt(args[0]);
            Integer subId = Integer.parseInt(args[1]);
            tag.setTag(serverType, areaId, subId);
        }

        initDB();

        GlobalServerSessionMng.getInstance().init();
        ClientSessionMng.getInstance().init();
        ZoneServerSessionMng.getInstance().init();
        ManagerServerSessionMng.getInstance().init();

        connectManager = new ConnectionManager();
        initConnectManager(connectManager);
        initMainThread("GateDriverThread");
    }

    @Override
    public void start() {
        dbThread.start();
        super.start();
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        ClientIdGenerater.GenerateId();
        GM2GIdGenerater.GenerateId();
        G2GMIdGenerater.GenerateId();
        Z2GIdGenerater.GenerateId();
        G2ZIdGenerater.GenerateId();
        M2GIdGenerater.GenerateId();
        G2MIdGenerater.GenerateId();
    }

    @Override
    public void initPath() {
        PathManager.getInstance().initPath();
    }

    @Override
    public void initLibData() {
        AuthorizationMng.getInstance().loadLibData();
    }

    @Override
    public void initXmlData() {
        List<File> fileList = new ArrayList<>();
        FileUtil.findFiles(PathManager.getInstance().getXmlPath(), "*xml", fileList);
        for (Object obj : fileList) {
            File f = (File) obj;
//            System.out.println("-----"+f.toString());
            DataListManager.getInstance().Parse(f.toString());
        }
    }

//    @lombok.Getter
//    private int managerWatchDogGroupId;
//
//    @lombok.Getter
//    private int managerWatchDogSubId;
//
//    private void initWatchDog() {
//        DataList serverList = DataListManager.getInstance().getDataList("ServerConfig");
//
//       for (Data date : serverList.values()) {
//            boolean watchdog = date.getBoolean("watchdog");
//            String type = date.getString("type");
//            if (watchdog && type.equals(ServerType.Manager.toString())) {
//                managerWatchDogGroupId = date.getInteger("areaId");
//                managerWatchDogSubId = date.getInteger("subId");
//            }
//        }
//    }

    @Override
    public void initLogger() {

    }

    public void initDB() {
        db = new DBManager();
        db.initConfig(PathManager.getInstance().getDBPath());
        db.init();
        dbThread = new DBDriverThread("GateDBThread", db);
    }

    @Override
    public void initOpenServerTime() {

    }

    @Override
    public void updateXml() {

    }

    @Override
    public void initService() {

    }

    @Override
    public void updateService(long dt) {
        updateDB();
    }

    private void updateDB() {
        Queue<AbstractDBOperator> queue = db.GetPostUpdateQueue();
        while (!queue.isEmpty())
        {
            AbstractDBOperator query = queue.poll();
            query.PostUpdate();
        }
    }
}

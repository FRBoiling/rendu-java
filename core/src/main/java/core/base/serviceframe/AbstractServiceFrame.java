package core.base.serviceframe;

import configuration.dataManager.*;
import core.base.model.ServerTag;
import core.network.ServiceState;
import gamedb.DBManager;
import gamedb.DBManagerPool;
import gamedb.DBMngPoolManager;
import gamedb.DBOperateType;
import gamedb.Util.MybatisConfigUtil;
import pathExt.PathManager;
import util.FileUtil;
import util.Time;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-09
 * Time: 13:52
 **/
public abstract class AbstractServiceFrame implements IService, IServiceFrame {
    private ServiceState state = ServiceState.STOPPED;
    public static ServerTag tag;
    public static long now;

    private DriverThread driverThread;
    private IConnectionManager connectManager;

    protected void initConnectManager(IConnectionManager connectManager) {
        this.connectManager = connectManager;
        initServers();
    }

    protected void initMainThread(String name) {
        driverThread = new DriverThread(name, this);
    }

    @Override
    public void init(String[] args) {
        initPath();
        initLogger();
        initXmlData();
        initLibData();
        initService();
        initOpenServerTime();
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
    public void update(long dt) {
        Time time = new Time();
        time.init();
        while (isOpened()) {
            try {
                now = time.init();
                long lastTime=time.update();
                updateService();
                connectManager.update(lastTime);
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
    public void initOpenServerTime() {

    }

    @Override
    public void initServers() {
        connectManager.init();
    }

    @Override
    public void updateXml() {

    }
//    //public DBMngPoolManager db;
//    public DBManager db;

//    @Override
//    public void initDB() {
//        //初始化Mybatis配置
//        List<File> fileList = new ArrayList<>();
//        FileUtil.findFiles(PathManager.getInstance().getXmlPath(), "mybatis_config.xml", fileList);
//        if (fileList.size() == 0) {
//            fileList.clear();
//            FileUtil.findFiles(System.getProperty("user.dir"), "mybatis_config.xml", fileList);
//        }
//        if (fileList.size() > 0) {
//            for (File file : fileList) {
//                MybatisConfigUtil.InitWithFile(file);
//                System.out.println("-------------- Mybatis Config Done---------------");
//                break;
//            }
//        } else {
//            System.out.println("--------------no mybatis_config.xml---------------");
//        }
//
//
//        //-----------------------------init DBMngPoolManager---------
//        db=new DBManager();
//        Thread dbThread = new Thread(()->db.Run());
//        dbThread.start();
//
////        db = new DBMngPoolManager();
////        DataList dbConfigs = DataListManager.getInstance().getDataList("DBConfig");
////        for (Map.Entry<Integer, Data> item : dbConfigs.entrySet()) {
////            String nickName = item.getValue().getName();
////            int poolCount = item.getValue().getInteger("threads");
////
////            DBManagerPool dbPool = new DBManagerPool(poolCount);
////            db.AddNameDb(nickName, dbPool);
////            dbPool.Init();
////        }
//
////        DataList tableList = DataListManager.getInstance().getDataList("DBTables");
////        for (Map.Entry<Integer, Data> item : tableList.entrySet()) {
////            String tableName = item.getValue().getName();
////            String writeDbName = item.getValue().getString("write");
////            String readDbName = item.getValue().getString("read");
////            DBManagerPool writeDb = db.GetDbByName(writeDbName);
////            if (writeDbName == null) {
////                System.out.println(String.format("can not get table %s write db", tableName));
////            }
////            db.AddTableDb(tableName, writeDb, DBOperateType.Write);
////            DBManagerPool readDb = db.GetDbByName(readDbName);
////            if (readDbName == null) {
////                System.out.println(String.format("can not get table %s read db", tableName));
////            }
////            db.AddTableDb(tableName, readDb, DBOperateType.Read);
////        }
//    }
//
//    @Override
//    public void initRedis() {
//
//    }


}

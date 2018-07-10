package zone;

import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.AbstractSystemFrame;
import core.base.serviceframe.DriverThread;
import core.base.serviceframe.IService;
import core.base.serviceframe.ISystemFrame;
import core.network.ServiceState;
import pathExt.PathManager;
import protocol.global.zone.GM2ZIdGenerater;
import protocol.server.register.ServerRegisterIdGenerater;
import protocol.zone.global.Z2GMIdGenerater;
import util.FileUtil;
import zone.connectionManager.ConnectManager;
import zone.global.GlobalServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gamedb.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:59
 */

public class ZoneServiceContext extends AbstractSystemFrame {
    public static ConnectManager connectMng;

    @Override
    public void init(String[] args) {
        super.init(args);

        ServerType serverType = ServerType.Zone;
        tag = new ServerTag();
        if (args.length>=2){
            Integer groupId = Integer.parseInt(args[0]);
            Integer subId = Integer.parseInt(args[1]);
            tag.setTag(serverType,groupId,subId);
        }
        connectMng = new ConnectManager();
        initConnectManager(connectMng);
        initMainThread("ZoneDriverThread");
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        GM2ZIdGenerater.GenerateId();
        Z2GMIdGenerater.GenerateId();
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

//    DBMngPoolManager db;

    @Override
    public void initDB() {
//        db = new DBMngPoolManager();
//        DataList dbList = DataListManager.inst.GetDataList("DBConfig");
//        foreach (var item : dbList)
//        {
//            string nickName = item.Value.Name;
//            string dbIp = item.Value.GetString("ip");
//            string dbName = item.Value.GetString("db");
//            string dbAccount = item.Value.GetString("account");
//            string dbPassword = item.Value.GetString("password");
//            string dbPort = item.Value.GetString("port");
//            string type = item.Value.GetString("type");
//            int poolCount = item.Value.GetInt("threads");
//
//            DBManagerPool dbPool = new DBManagerPool(poolCount);
//            db.AddNameDb(nickName, dbPool);
//            dbPool.Init(dbIp, dbName, dbAccount, dbPassword, dbPort);
//        }
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

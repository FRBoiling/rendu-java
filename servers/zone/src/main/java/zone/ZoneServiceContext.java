package zone;

import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.AbstractSystemFrame;
import gamedb.DBManagerPool;
import gamedb.DBMngPoolManager;
import gamedb.DBOperateType;
import gamedb.Util.MybatisConfigUtil;
import pathExt.PathManager;
import protocol.global.zone.GM2ZIdGenerater;
import protocol.server.register.ServerRegisterIdGenerater;
import protocol.zone.global.Z2GMIdGenerater;
import util.FileUtil;
import zone.connectionManager.ConnectManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import configuration.dataManager.*;


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

    DBMngPoolManager db;

    @Override
    public void initDB() {

        //初始化Mybatis配置
        List<File> fileList = new ArrayList<File>();
        FileUtil.findFiles(PathManager.getInstance().getXmlPath(),"mybatis_config.xml",fileList);
        if(fileList.size()==0){
            fileList.clear();
            FileUtil.findFiles(System.getProperty("user.dir"),"mybatis_config.xml",fileList);
        }
        if(fileList.size()>0){
            for (File file:fileList) {
                MybatisConfigUtil.InitWithFile(file);
                System.out.println("-------------- Mybatis Config Done---------------");
                break;
            }
        }else {
            System.out.println("--------------no mybatis_config.xml---------------");
        }


        //-----------------------------init DBMngPoolManager---------
        db=new DBMngPoolManager();
        DataList dbconfigs=DataListManager.getInstance().getDataList("DBConfig");
        for (Map.Entry<Integer, Data> item  :dbconfigs.entrySet())
        {
            String nickName = item.getValue().getName();
            int poolCount = item.getValue().getInteger("threads");

            DBManagerPool dbPool = new DBManagerPool(poolCount);
            db.AddNameDb(nickName, dbPool);
            dbPool.Init();
        }

        DataList tableList = DataListManager.getInstance().getDataList("DBTables");
        for (Map.Entry<Integer, Data> item  :tableList.entrySet())
        {
            String tableName = item.getValue().getName();
            String writeDbName = item.getValue().getString("write");
            String readDbName = item.getValue().getString("read");
            DBManagerPool writeDb = db.GetDbByName(writeDbName);
            if (writeDbName == null)
            {
                System.out.println(String.format("can not get table %s write db", tableName));
            }
            db.AddTableDb(tableName, writeDb,DBOperateType.Write);
            DBManagerPool readDb = db.GetDbByName(readDbName);
            if (readDbName == null)
            {
                System.out.println(String.format("can not get table %s read db", tableName));
            }
            db.AddTableDb(tableName, readDb, DBOperateType.Read);
        }
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

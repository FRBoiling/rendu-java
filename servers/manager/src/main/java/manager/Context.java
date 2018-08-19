package manager;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.*;
import gamedb.DBManager;
import gamedb.dao.AbstractDBOperator;
import gamedb.dao.role.MaxRoleUidDBOperator;
import lombok.extern.slf4j.Slf4j;
import manager.connectionManager.ConnectionManager;
import manager.gate.GateServerSessionMng;
import manager.global.GlobalServerSessionMng;
import manager.relation.RelationServerSessionMng;
import manager.zone.ZoneServerSessionMng;
import pathExt.PathManager;
import protocol.gate.manager.G2MIdGenerater;
import protocol.global.manager.GM2MIdGenerater;
import protocol.manager.gate.M2GIdGenerater;
import protocol.manager.global.M2GMIdGenerater;
import protocol.manager.manager.M2MIdGenerater;
import protocol.manager.relation.M2RIdGenerater;
import protocol.manager.zone.M2ZIdGenerater;
import protocol.relation.manager.R2MIdGenerater;
import protocol.server.register.ServerRegister;
import protocol.server.register.ServerRegisterIdGenerater;
import protocol.zone.manager.Z2MIdGenerater;
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
@Slf4j
public class Context extends AbstractServiceFrame {

    public static ConnectionManager connectManager;

    public DBManager db;
    private DBDriverThread dbThread;

    //    private boolean watchDog = false;
    public int maxRoleUid;

    @Override
    public void init(String[] args) {
        super.init(args);

        ServerType serverType = ServerType.Manager;
        tag = new ServerTag();
        Integer groupId = 0;
        Integer subId = 0;
        if (args.length >= 1) {
            groupId = Integer.parseInt(args[0]);
            tag.setTag(serverType, groupId, 0);
        }

        initDB();

        GlobalServerSessionMng.getInstance().init();
        GateServerSessionMng.getInstance().init();
        ZoneServerSessionMng.getInstance().init();
//        ManagerServerSessionMng.getInstance().setContext(this);
        RelationServerSessionMng.getInstance().init();

        connectManager = new ConnectionManager();
        initConnectManager(connectManager);
        initMainThread("ManagerDriverThread");
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        GM2MIdGenerater.GenerateId();
        M2GMIdGenerater.GenerateId();
        M2MIdGenerater.GenerateId();
        Z2MIdGenerater.GenerateId();
        M2ZIdGenerater.GenerateId();
        R2MIdGenerater.GenerateId();
        M2RIdGenerater.GenerateId();
        G2MIdGenerater.GenerateId();
        M2GIdGenerater.GenerateId();
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
        FileUtil.findFiles(PathManager.getInstance().getXmlPath(), "*xml", fileList);
        for (Object obj : fileList) {
            File f = (File) obj;
//            System.out.println("-----"+f.toString());
            DataListManager.getInstance().Parse(f.toString());
        }
    }

    @Override
    public void initLogger() {

    }

    public void initDB() {
        db = new DBManager();
        db.initConfig(PathManager.getInstance().getDBPath());
        db.init();
        dbThread = new DBDriverThread("ManagerDBThread", db);

//        for (int i = 0; i < DBProxyDefault.TableBaseCount; i++) {
        //String charTableName = db.GetTableName("player", i, DBTableParamType.Character);
        //DBManagerPool charDBPool = db.GetWriteDbByName(charTableName);
        MaxRoleUidDBOperator operator = new MaxRoleUidDBOperator();
        db.Call(operator, (result) ->
        {
            if( operator.getResult()==1){
                int max = 0;
                if (operator.maxUid != null){
                    max = operator.maxUid;
                }
                maxRoleUid = maxRoleUid > max ? maxRoleUid : max;
                log.info("table {} max char uid {}", "role", maxRoleUid);
            }
        });
//        }
    }

    @Override
    public void start() {
        dbThread.start();
        super.start();
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

    public static List<ServerRegister.LISTEN_INFO> getListenInfoList(){
        List<ServerRegister.LISTEN_INFO> listen_info_list = new ArrayList<>();
        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData =dateList.getData(tag.toString());


        int zonePort = serviceData.getInteger("zonePort");
        int gatePort = serviceData.getInteger("gatePort");
        int relationPort = serviceData.getInteger("relationPort");

        ServerRegister.LISTEN_INFO.Builder zoneInfo = ServerRegister.LISTEN_INFO.newBuilder();
        zoneInfo.setServerType(ServerType.Zone.ordinal());
        zoneInfo.setPort(zonePort);
        listen_info_list.add(zoneInfo.build());

        ServerRegister.LISTEN_INFO.Builder gateInfo = ServerRegister.LISTEN_INFO.newBuilder();
        gateInfo.setServerType(ServerType.Gate.ordinal());
        gateInfo.setPort(gatePort);
        listen_info_list.add(gateInfo.build());

        ServerRegister.LISTEN_INFO.Builder relationInfo = ServerRegister.LISTEN_INFO.newBuilder();
        relationInfo.setServerType(ServerType.Relation.ordinal());
        relationInfo.setPort(relationPort);
        listen_info_list.add(relationInfo.build());

        return listen_info_list;
    }
}

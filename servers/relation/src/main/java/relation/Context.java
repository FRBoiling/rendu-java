package relation;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.AbstractServiceFrame;
import pathExt.PathManager;
import protocol.global.relation.GM2RIdGenerater;
import protocol.manager.relation.M2RIdGenerater;
import protocol.relation.global.R2GMIdGenerater;
import protocol.relation.manager.R2MIdGenerater;
import protocol.relation.relation.R2RIdGenerater;
import protocol.relation.zone.R2ZIdGenerater;
import protocol.server.register.ServerRegister;
import protocol.server.register.ServerRegisterIdGenerater;
import protocol.zone.relation.Z2RIdGenerater;
import relation.connectionManager.ConnectionManager;
import relation.global.GlobalServerSessionMng;
import relation.manager.ManagerServerSessionMng;
import relation.zone.ZoneServerSessionMng;
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

public class Context extends AbstractServiceFrame {
    public static ConnectionManager connectManager;

    @Override
    public void init(String[] args) {
        super.init(args);

        ServerType serverType = ServerType.Relation;
        tag = new ServerTag();
        if (args.length >= 1) {
            Integer groupId = Integer.parseInt(args[0]);
            tag.setTag(serverType, groupId, 0);
        }

        GlobalServerSessionMng.getInstance().init();
        ZoneServerSessionMng.getInstance().init();
        ManagerServerSessionMng.getInstance().init();
//        RelationServerSessionMng.getInstance().setContext(this);

        connectManager = new ConnectionManager();
        initConnectManager(connectManager);
        initMainThread("RelationDriverThread");
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        GM2RIdGenerater.GenerateId();
        R2GMIdGenerater.GenerateId();
        M2RIdGenerater.GenerateId();
        R2MIdGenerater.GenerateId();
        Z2RIdGenerater.GenerateId();
        R2ZIdGenerater.GenerateId();
        R2RIdGenerater.GenerateId();
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

    }

    public static List<ServerRegister.LISTEN_INFO> getListenInfoList(){
        List<ServerRegister.LISTEN_INFO> listen_info_list = new ArrayList<>();
        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData =dateList.getData(tag.toString());

        int zonePort = serviceData.getInteger("zonePort");

        ServerRegister.LISTEN_INFO.Builder zoneInfo = ServerRegister.LISTEN_INFO.newBuilder();
        zoneInfo.setServerType(ServerType.Zone.ordinal());
        zoneInfo.setPort(zonePort);
        listen_info_list.add(zoneInfo.build());

        return listen_info_list;
    }
}

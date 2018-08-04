package gate;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.AbstractSystemFrame;
import gate.client.AuthorizationMng;
import gate.connectionManager.ConnectManager;
import pathExt.PathManager;
import protocol.client.c2g.C2GIdGenerater;
import protocol.client.g2c.G2CIdGenerater;
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
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:59
 */

public class Context extends AbstractSystemFrame {
    public static ConnectManager connectManager;

    @Override
    public void init(String[] args) {
        super.init(args);

        ServerType serverType = ServerType.Gate;
        tag = new ServerTag();
        if (args.length >= 1) {
            Integer subId = Integer.parseInt(args[0]);
            tag.setTag(serverType, 0, subId);
        }
        connectManager = new ConnectManager();
        initConnectManager(connectManager);
        initMainThread("GateDriverThread");
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        C2GIdGenerater.GenerateId();
        G2CIdGenerater.GenerateId();
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
        initWatchDog();
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

    @lombok.Getter
    private int managerWatchDogGroupId;

    @lombok.Getter
    private int managerWatchDogSubId;

    private void initWatchDog() {
        DataList serverList = DataListManager.getInstance().getDataList("ServerConfig");

        for (Map.Entry<Integer, Data> item : serverList.entrySet()) {
            //String nickName = item.getValue().getName();
            boolean watchdog = item.getValue().getBoolean("watchdog");
            String type = item.getValue().getString("type");
            if (watchdog && type.equals("Manager")) {
                managerWatchDogGroupId = item.getValue().getInteger("groupId");
                managerWatchDogSubId = item.getValue().getInteger("subId");
            }
        }

//       for (Data date : serverList.values()) {
//            boolean watchdog = date.getBoolean("watchdog");
//            String type = date.getString("type");
//            if (watchdog && type.equals(ServerType.Manager.toString())) {
//                managerWatchDogGroupId = date.getInteger("groupId");
//                managerWatchDogSubId = date.getInteger("subId");
//            }
//        }
    }

    @Override
    public void initLogger() {

    }

    @Override
    public void initDB() {
        super.initDB();
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

package global;

import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.AbstractSystemFrame;
import core.base.serviceframe.DriverThread;
import core.base.serviceframe.IService;
import core.base.serviceframe.ISystemFrame;
import core.network.ServiceState;
import global.connectionManager.ConnectManager;
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

public class GlobalServiceContext extends AbstractSystemFrame {
    public static  ConnectManager  connectMng;
    @Override
    public void init(String[] args){
        super.init(args);

        ServerType serverType = ServerType.Global;
        tag = new ServerTag();
        tag.setTag(serverType,0,0);

        connectMng = new ConnectManager();
        initConnectManager(connectMng);
        initMainThread("GlobalDriverThread");
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

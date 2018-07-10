package manager;

import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.*;
import core.network.ServiceState;
import manager.connectionManager.ConnectManager;
import manager.global.GlobalServer;
import manager.zone.ZoneServer;
import pathExt.PathManager;
import protocol.global.manager.GM2MIdGenerater;
import protocol.manager.global.M2GMIdGenerater;
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

public class ManagerServiceContext extends AbstractSystemFrame {
    public static ConnectManager connectMng;

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
        connectMng = new ConnectManager();
        initConnectManager(connectMng);
        initMainThread("ManagerDriverThread");
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        GM2MIdGenerater.GenerateId();
        M2GMIdGenerater.GenerateId();
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

package relation;

import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.AbstractSystemFrame;
import core.base.serviceframe.DriverThread;
import core.base.serviceframe.IService;
import core.base.serviceframe.ISystemFrame;
import core.network.ServiceState;
import pathExt.PathManager;
import protocol.global.relation.GM2RIdGenerater;
import protocol.relation.global.R2GMIdGenerater;
import protocol.server.register.ServerRegisterIdGenerater;
import relation.connectionManager.ConnectManager;
import relation.global.GlobalServer;
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

public class RelationServiceContext extends AbstractSystemFrame {
    public static ConnectManager connectMng;

    @Override
    public void init(String[] args) {
        super.init(args);

        ServerType serverType = ServerType.Relation;
        tag = new ServerTag();
        if (args.length>=1){
            Integer groupId = Integer.parseInt(args[0]);
            tag.setTag(serverType,groupId,0);
        }
        connectMng= new ConnectManager();
        initConnectManager(connectMng);
        initMainThread("RelationDriverThread");
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        GM2RIdGenerater.GenerateId();
        R2GMIdGenerater.GenerateId();
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
        List<File> fileList = new ArrayList<File> ();
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

package relation;

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
import protocol.server.register.ServerRegisterIdGenerater;
import protocol.zone.relation.Z2RIdGenerater;
import relation.connectionManager.ConnectionManager;
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

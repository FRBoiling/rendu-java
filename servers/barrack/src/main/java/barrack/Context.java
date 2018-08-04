package barrack;

import barrack.connectionManager.ConnectManager;
import configuration.dataManager.DataListManager;
import core.base.model.ServerTag;
import core.base.model.ServerType;
import core.base.serviceframe.AbstractSystemFrame;
import pathExt.PathManager;
import protocol.gate.global.G2GMIdGenerater;
import protocol.global.gate.GM2GIdGenerater;
import protocol.server.register.ServerRegisterIdGenerater;
import util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-13
 * Time: 09:59
 */

public class Context extends AbstractSystemFrame {

    public static ConnectManager connectManager;
    @Override
    public void init(String[] args) {
        super.init(args);

        ServerType serverType = ServerType.Gate;
        tag = new ServerTag();
        if (args.length>=1){
            Integer subId = Integer.parseInt(args[0]);
            tag.setTag(serverType,0,subId);
        }
        connectManager =  new ConnectManager();
        initConnectManager(connectManager);
        initMainThread("BarrackDriverThread");
    }

    @Override
    public void intiProtocol() {
        ServerRegisterIdGenerater.GenerateId();
        GM2GIdGenerater.GenerateId();
        G2GMIdGenerater.GenerateId();
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

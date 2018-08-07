package manager.gate;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.serviceframe.AbstractServer;
import manager.Context;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 9:47
 */

public class GateAcceptor extends AbstractServer {
    @Override
    public void init(String[] args) {
        acceptorGroupCount=1;
        IOGroupCount =SystemConst.AVAILABLE_PROCESSORS;

        responseMng = new GateServerResponseMng();
        sessionMng = GateServerSessionMng.getInstance();

        setName(getClass().getSimpleName());

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData =dateList.getData(Context.tag.toString());
        listenPort = serviceData.getInteger("gatePort");
        bind(listenPort);
    }
}

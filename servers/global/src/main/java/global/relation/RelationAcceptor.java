package global.relation;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.serviceframe.AbstractServer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 9:47
 */

public class RelationAcceptor extends AbstractServer {
    @Override
    public void init(String[] args) {
        acceptorGroupCount=1;
        IOGroupCount =SystemConst.AVAILABLE_PROCESSORS;
        responseMng = new RelationServerResponseMng();
        sessionMng = RelationServerSessionMng.getInstance();

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData =dateList.getData("Global");
        listenPort = serviceData.getInteger("relationPort");
        bind(listenPort);
    }
}

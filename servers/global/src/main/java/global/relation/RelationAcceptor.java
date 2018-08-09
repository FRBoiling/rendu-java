package global.relation;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.serviceframe.AbstractAcceptor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 9:47
 */

public class RelationAcceptor extends AbstractAcceptor {
    @Override
    public void init(String[] args) {
        acceptorGroupCount = 1;
        IOGroupCount = SystemConst.AVAILABLE_PROCESSORS;

        sessionMng = RelationServerSessionMng.getInstance();

        setName(getClass().getSimpleName());

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData = dateList.getData("Global");
        listenPort = serviceData.getInteger("relationPort");
        bind(listenPort);
    }
}

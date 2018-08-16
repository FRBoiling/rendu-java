package global.zone;

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

public class ZoneAcceptor extends AbstractAcceptor {
    @Override
    public void init(String[] args) {
        acceptorGroupCount = 1;
        IOGroupCount = 1;

        sessionMng = ZoneServerSessionMng.getInstance();

        setName(getClass().getSimpleName());

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData = dateList.getData("Global");
        listenPort = serviceData.getInteger("zonePort");
        bind(listenPort);
    }
}

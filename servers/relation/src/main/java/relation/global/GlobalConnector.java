package relation.global;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.serviceframe.AbstractConnector;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */

public class GlobalConnector extends AbstractConnector {
    @Override
    public void init(String[] args) {
        IOGroupCount = SystemConst.AVAILABLE_PROCESSORS;

        sessionMng = GlobalServerSessionMng.getInstance();

        setName(getClass().getSimpleName());

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data globalData = dateList.getData("Global");
        ip = globalData.getString("ip");
        port = globalData.getInteger("relationPort");
        connect(ip, port);
    }
}

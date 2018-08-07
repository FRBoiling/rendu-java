package gate.global;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.serviceframe.AbstractClient;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */
@Slf4j
public class GlobalConnector extends AbstractClient {
    @Override
    public void init(String[] args) {
        IOGroupCount = SystemConst.AVAILABLE_PROCESSORS;
        responseMng = new GlobalServerResponseMng();
        sessionMng = GlobalServerSessionMng.getInstance();
        setName(getClass().getSimpleName());

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data  globalData =dateList.getData("Global");
        ip = globalData.getString("ip");
        port = globalData.getInteger("gatePort");
        connect(ip,port);
    }
}

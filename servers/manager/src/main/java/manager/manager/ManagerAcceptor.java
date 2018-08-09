package manager.manager;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.serviceframe.AbstractAcceptor;
import lombok.extern.slf4j.Slf4j;
import manager.Context;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */
@Slf4j
public class ManagerAcceptor extends AbstractAcceptor {
    @Override
    public void init(String[] args) {
        acceptorGroupCount = 1;
        IOGroupCount = SystemConst.AVAILABLE_PROCESSORS;

        sessionMng = ManagerServerSessionMng.getInstance();

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData = dateList.getData(Context.tag.toString());
        listenPort = serviceData.getInteger("managerPort");
        bind(listenPort);
    }
}

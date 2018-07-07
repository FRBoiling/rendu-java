package manager.global;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.serviceframe.IService;
import core.network.MsgRouter;
import core.network.NetworkListener;
import core.network.ServiceState;
import core.network.client.ClientNetworkService;
import core.network.client.ClientNetworkServiceBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */
@Slf4j
public class GlobalServer implements IService {
    private final ClientNetworkService netWork;
    private ServiceState state;

    private Data globalData;

    private String globalIp;
    private int globalPort;

    public GlobalServer() throws Exception {
        init(null);

        int IOGroupCount = SystemConst.AVAILABLE_PROCESSORS;

        GlobalServerResponseMng responseMng = new GlobalServerResponseMng();
        responseMng.register();

        ClientNetworkServiceBuilder builder = new ClientNetworkServiceBuilder();
        builder.setWorkerLoopGroupCount(IOGroupCount);
        builder.setIp(globalIp);
        builder.setPort(globalPort);
        builder.setConsumer(new MsgRouter());
        builder.setListener(new NetworkListener(GlobalServerSessionMng.getInstance(),responseMng));

        // 创建网络服务
        netWork = (ClientNetworkService) builder.createService();
    }

    @Override
    public void init(String[] args) {
        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        globalData =dateList.getData("GlobalServer");
        globalIp = globalData.getString("ip");
        globalPort = globalData.getInteger("managerPort");
    }

    @Override
    public void update() {
        GlobalServerSessionMng.getInstance().update();
    }

    @Override
    public void start() {
        netWork.start();
        if (netWork.isOpened()) {
            state = ServiceState.RUNNING;
        }
    }

    @Override
    public void stop() {
        netWork.stop();
        state = ServiceState.STOPPED;
    }

    @Override
    public ServiceState getState() {
        return state;
    }


}

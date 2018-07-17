package gate.client;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.common.AbstractSessionManager;
import core.base.serviceframe.IService;
import core.network.IResponseHandlerManager;
import core.network.MsgRouter;
import core.network.NetworkListener;
import core.network.ServiceState;
import core.network.server.ServerNetworkServiceBuilder;
import core.network.server.forClientApp.NetworkService;
import core.network.server.forClientApp.NetworkServiceBuilder;
import gate.GateServiceContext;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 9:47
 */

public class Client implements IService {
    private final NetworkServiceBuilder builder;

    private NetworkService netWork;

    protected IResponseHandlerManager responseMng;
    protected AbstractSessionManager sessionMng;

    protected ServiceState state;
    protected int acceptorGroupCount;
    protected int IOGroupCount;

    protected int listenPort;

    public Client(){
        builder= new NetworkServiceBuilder();
        acceptorGroupCount=1;
        IOGroupCount =1;
        state = ServiceState.STOPPED;
        init(null);
    }

    @Override
    public void init(String[] args) {
        acceptorGroupCount=1;
        IOGroupCount =SystemConst.AVAILABLE_PROCESSORS;

        responseMng = new ClientResponseMng();
        sessionMng = ClientSessionMng.getInstance();

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData =dateList.getData(GateServiceContext.tag.getKey());
        listenPort = serviceData.getInteger("port");

        builder.setPort(listenPort);
        builder.setAcceptorGroupCount(acceptorGroupCount);
        builder.setIOGroupCount(IOGroupCount);
        builder.setConsumer(new MsgRouter());
        builder.setListener(new NetworkListener(sessionMng,responseMng));
        // 创建网络服务
        netWork = (NetworkService) builder.createService();
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

    @Override
    public void update() {
        sessionMng.update();
    }
}

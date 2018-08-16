package gate.client;

import configuration.dataManager.Data;
import configuration.dataManager.DataList;
import configuration.dataManager.DataListManager;
import constant.SystemConst;
import core.base.common.AbstractSessionManager;
import core.base.serviceframe.IService;
import core.network.MsgRouter;
import core.network.NetworkListener;
import core.network.ServiceState;
import core.network.acceptor.forClientApp.AcceptorNetworkService_client;
import core.network.acceptor.forClientApp.AcceptorNetworkServiceBuilder_client;
import gate.Context;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 9:47
 */
@Slf4j
public class ClientAcceptor implements IService {
    private final AcceptorNetworkServiceBuilder_client builder;

    private AcceptorNetworkService_client netWork;

    private ServiceState state;
    private int acceptorGroupCount;
    private int IOGroupCount;

    public ClientAcceptor(){
        builder= new AcceptorNetworkServiceBuilder_client();
        acceptorGroupCount=1;
        IOGroupCount =1;
        state = ServiceState.STOPPED;
        init(null);
    }

    @Override
    public void init(String[] args) {
        acceptorGroupCount=1;
        IOGroupCount = SystemConst.AVAILABLE_PROCESSORS / 2;

        AbstractSessionManager sessionMng = ClientSessionMng.getInstance();

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData =dateList.getData(Context.tag.toString());
        int listenPort = serviceData.getInteger("port");

        builder.setName(getClass().getSimpleName());
        builder.setPort(listenPort);
        builder.setAcceptorGroupCount(acceptorGroupCount);
        builder.setIOGroupCount(IOGroupCount);
        builder.setConsumer(new MsgRouter());
        builder.setListener(new NetworkListener(sessionMng));
        // 创建网络服务
        netWork = (AcceptorNetworkService_client) builder.createService();
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
    public void update(long dt) {
    }
}

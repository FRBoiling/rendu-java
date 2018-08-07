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
import core.network.server.forClientApp.NetworkService;
import core.network.server.forClientApp.NetworkServiceBuilder;
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
    private final NetworkServiceBuilder builder;

    private NetworkService netWork;

    private ServiceState state;
    private int acceptorGroupCount;
    private int IOGroupCount;

    public ClientAcceptor(){
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

        IResponseHandlerManager responseMng = new ClientResponseMng();
        AbstractSessionManager sessionMng = ClientSessionMng.getInstance();

        DataList dateList = DataListManager.getInstance().getDataList("ServerConfig");
        Data serviceData =dateList.getData(Context.tag.toString());
        int listenPort = serviceData.getInteger("port");

        builder.setName(getClass().getSimpleName());
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
    public void update(long dt) {
    }
}

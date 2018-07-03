package global.gate;

import constant.SystemConst;
import core.base.concurrent.QueueDriver;
import core.base.concurrent.QueueExecutor;
import core.base.serviceframe.IService;
import core.network.NetworkListener;
import core.network.ServiceState;
import core.network.server.ServerNetworkService;
import core.network.server.ServerNetworkServiceBuilder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 9:47
 */

public class GateServer implements IService {
    private ServerNetworkService netWork;
    private ServiceState state;

    public GateServer() throws Exception {
        int acceptorGroupCount = 1;
        int IOGroupCount = SystemConst.AVAILABLE_PROCESSORS;

        GateServerResponseMng responseMng = new GateServerResponseMng();
        responseMng.register();
        GateServerMsgRouter msgRouter = new GateServerMsgRouter(responseMng);

        ServerNetworkServiceBuilder builder = new ServerNetworkServiceBuilder();
        builder.setResponseHandlerManager(responseMng);
        builder.setConsumer(msgRouter);
        builder.setAcceptorGroupCount(acceptorGroupCount);
        builder.setIOGroupCount(IOGroupCount);
        builder.setPort(9002);
        builder.setListener(new NetworkListener(GateServerSessionMng.getInstance()));

        // 创建网络服务
        netWork = (ServerNetworkService) builder.createService();
    }

    @Override
    public void init(String[] args) {

    }
    @Override
    public void update() {
        GateServerSessionMng.getInstance().update();
    }
    @Override
    public void start() {
        netWork.start();
        if (netWork.isOpened()) {
            state =ServiceState.RUNNING;
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

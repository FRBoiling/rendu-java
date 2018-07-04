package gate.global;

import constant.SystemConst;
import core.base.serviceframe.IService;
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

    public GlobalServer() throws Exception {
//        int IOGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8
//                : Runtime.getRuntime().availableProcessors();
        int IOGroupCount = SystemConst.AVAILABLE_PROCESSORS;

        GlobalServerResponseMng responseMng = new GlobalServerResponseMng();
        responseMng.register();

        GlobalServerMsgRouter msgRouter = new GlobalServerMsgRouter();
        ClientNetworkServiceBuilder builder = new ClientNetworkServiceBuilder();
        builder.setConsumer(msgRouter);
        builder.setWorkerLoopGroupCount(IOGroupCount);
        builder.setIp("127.0.0.1");
        builder.setPort(9002);
        builder.setListener(new NetworkListener(GlobalServerSessionMng.getInstance(),responseMng));

        // 创建网络服务
        netWork = (ClientNetworkService) builder.createService();
    }

    @Override
    public void init(String[] args) {

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

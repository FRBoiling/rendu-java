package core.base.serviceframe;

import core.base.common.AbstractSessionManager;
import core.network.IResponseHandlerManager;
import core.network.MsgRouter;
import core.network.NetworkListener;
import core.network.ServiceState;
import core.network.client.ClientNetworkService;
import core.network.client.ClientNetworkServiceBuilder;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-09
 * Time: 10:22
 **/
public abstract class AbstractClient implements IService {
    private final ClientNetworkServiceBuilder builder;

    private ClientNetworkService netWork;

    protected IResponseHandlerManager responseMng;
    protected AbstractSessionManager sessionMng;

    protected ServiceState state;
    protected int IOGroupCount;

    protected String ip;
    protected int port;

    public AbstractClient(){
        builder= new ClientNetworkServiceBuilder();
        IOGroupCount =1;
        state = ServiceState.STOPPED;
        init(null);
    }

    public void connect(String ip,int port){
        this.ip = ip;
        this.port = port;
        builder.setIp(ip);
        builder.setPort(port);
        builder.setWorkerLoopGroupCount(IOGroupCount);
        builder.setConsumer(new MsgRouter());
        builder.setListener(new NetworkListener(sessionMng,responseMng));
        // 创建网络服务
        netWork = (ClientNetworkService) builder.createService();
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

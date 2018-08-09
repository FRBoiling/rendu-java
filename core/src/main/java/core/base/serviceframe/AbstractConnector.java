package core.base.serviceframe;

import core.base.common.AbstractSessionManager;
import core.network.IResponseHandlerManager;
import core.network.MsgRouter;
import core.network.NetworkListener;
import core.network.ServiceState;
import core.network.connector.ConnectorNetworkService;
import core.network.connector.ConnectorNetworkServiceBuilder;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-09
 * Time: 10:22
 **/
public abstract class AbstractConnector implements IService {
    private final ConnectorNetworkServiceBuilder builder;

    private ConnectorNetworkService netWork;

    protected IResponseHandlerManager responseMng;
    protected AbstractSessionManager sessionMng;

    protected ServiceState state;
    protected int IOGroupCount;

    protected String ip;
    protected int port;

    public AbstractConnector(){
        builder= new ConnectorNetworkServiceBuilder();
        IOGroupCount =1;
        state = ServiceState.STOPPED;
    }

    public void setName(String name){
        builder.setName(name);
    }

    public void connect(String ip,int port){
        this.ip = ip;
        this.port = port;
        builder.setIp(ip);
        builder.setPort(port);
        builder.setWorkerLoopGroupCount(IOGroupCount);
        builder.setConsumer(new MsgRouter());
        builder.setListener(new NetworkListener(sessionMng));
        // 创建网络服务
        netWork = (ConnectorNetworkService) builder.createService();
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
    public void update(long dt){
    }
}

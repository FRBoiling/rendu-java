package core.base.serviceframe;

import core.base.common.AbstractSessionManager;
import core.network.IResponseHandlerManager;
import core.network.MsgRouter;
import core.network.NetworkListener;
import core.network.ServiceState;
import core.network.acceptor.AcceptorNetworkService;
import core.network.acceptor.AcceptorNetworkServiceBuilder;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boiling
 * Date: 2018-07-09
 * Time: 10:23
 **/
public abstract class AbstractAcceptor implements IService{
    private final AcceptorNetworkServiceBuilder builder;

    private AcceptorNetworkService netWork;

    protected IResponseHandlerManager responseMng;
    protected AbstractSessionManager sessionMng;

    protected ServiceState state;
    protected int acceptorGroupCount;
    protected int IOGroupCount;

    protected int listenPort;

    public AbstractAcceptor(){
        builder= new AcceptorNetworkServiceBuilder();
        acceptorGroupCount = 1;
        IOGroupCount = 1;
        state = ServiceState.STOPPED;
        init(null);
    }

    public void setName(String name){
        builder.setName(name);
    }

    public void bind(int port){
        this.listenPort = port;
        builder.setPort(port);
        builder.setAcceptorGroupCount(acceptorGroupCount);
        builder.setIOGroupCount(IOGroupCount);
        builder.setConsumer(new MsgRouter());
        builder.setListener(new NetworkListener(sessionMng));
        // 创建网络服务
        netWork = (AcceptorNetworkService) builder.createService();
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

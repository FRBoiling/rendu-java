package global.gate;

import core.base.common.IServer;
import core.network.NetworkListener;
import core.network.server.ServerNetworkService;
import core.network.server.ServerNetworkServiceBuilder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 9:47
 */

public class GateServer implements IServer {
    private ServerNetworkService netWork;
    private boolean state;

    public GateServer() throws Exception {
        int bossLoopGroupCount = 4;
        int workerLoopGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8
                : Runtime.getRuntime().availableProcessors();

        GateServerResponseMng responseMng = new GateServerResponseMng();
        GateServerMsgRouter msgRouter = new GateServerMsgRouter();

        ServerNetworkServiceBuilder builder = new ServerNetworkServiceBuilder();
        builder.setMessageAndHandler(responseMng);
        builder.setConsumer(msgRouter);
        builder.setAcceptorGroupCount(bossLoopGroupCount);
        builder.setIOGroupCount(workerLoopGroupCount);
        builder.setPort(8201);
        builder.setListener(new NetworkListener());

        // 创建网络服务
        netWork = (ServerNetworkService) builder.createService();
    }

    @Override
    public void start() {
        netWork.start();
        if (netWork.isOpened()) {
            state =true;
        }
    }

    @Override
    public void stop() {
        netWork.stop();
        state = false;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean getState() {
        return state;
    }
}

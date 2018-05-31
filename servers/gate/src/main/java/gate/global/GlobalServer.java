package gate.global;

import core.base.common.IServer;
import core.network.NetworkListener;
import core.network.client.ClientNetworkService;
import core.network.client.ClientNetworkServiceBuilder;
import core.network.server.ServerNetworkService;
import core.network.server.ServerNetworkServiceBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */
@Slf4j
public class GlobalServer implements IServer {
    private ClientNetworkService netWork;
    private boolean state;

    public GlobalServer() throws Exception {
        int IOGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8
                : Runtime.getRuntime().availableProcessors();

        GlobalServerResponseMng responseMng = new GlobalServerResponseMng();
        GlobalServerMsgRouter msgRouter = new GlobalServerMsgRouter();
        GlobalServerSessionMng sessionMng = new GlobalServerSessionMng();

        ClientNetworkServiceBuilder builder = new ClientNetworkServiceBuilder();
        builder.setMessageAndHandler(responseMng);
        builder.setConsumer(msgRouter);
        builder.setWorkerLoopGroupCount(IOGroupCount);
        builder.setIp("127.0.0.1");
        builder.setPort(8201);
        builder.setListener(new NetworkListener(sessionMng));

        // 创建网络服务
        netWork = (ClientNetworkService) builder.createService();
    }

    @Override
    public void start() {
        netWork.start();
        if (netWork.isOpened()) {
            state = true;
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

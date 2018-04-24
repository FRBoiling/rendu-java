package client;

import core.network.NetworkListener;
import core.network.client.ClientNetworkService;
import core.network.client.ClientNetworkServiceBuilder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 21:59
 */
public class Client {
    private ClientNetworkService netWork;

    private boolean state = false;

    private ClientMessageRouter router;

    public Client(ClientOption option) throws Exception {
        int workerLoopGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8
                : Runtime.getRuntime().availableProcessors();

        ClientMessageAndHandler pool = new ClientMessageAndHandler();

        router = new ClientMessageRouter(pool);
        ClientNetworkServiceBuilder builder = new ClientNetworkServiceBuilder();
        builder.setMessageAndHandler(pool);
        builder.setWorkerLoopGroupCount(workerLoopGroupCount);
        builder.setIp("127.0.0.1");
        builder.setPort(8201);
        builder.setListener(new NetworkListener());
        builder.setConsumer(router);
        router.initRouter();

        // 创建网络服务
        netWork = (ClientNetworkService) builder.createService();

//        //初始化配置文件
//        ConfigDataManager.getInstance().init();

//        // 注册事件
//        EventRegister.registerPreparedListeners();

//        //开启定时任务
//        ScheduleManager.getInstance().start();
    }

    public ClientMessageRouter getRouter() {
        return this.router;
    }

    public void start() {
        netWork.start();
        if (netWork.isOpened()) {
            state = true;
        }
    }

    public boolean isOpen() {
        return state;
    }

    public void stop() {
        netWork.stop();
        state = false;
    }
}

package client;

import core.network.server.ServerNetworkService;
import core.network.server.ServerNetworkServiceBuilder;
import server.GameMessageAndHandler;
import server.MessageRouter;
import server.NetworkListener;
import server.ServerOption;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 21:59
 */
public class GameClient {
    private ServerNetworkService netWork;

    private boolean state = false;

    private MessageRouter router;

    public GameServer(ServerOption option) throws Exception {
        int bossLoopGroupCount = 4;
        int workerLoopGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8
                : Runtime.getRuntime().availableProcessors();

        GameMessageAndHandler pool = new GameMessageAndHandler();

        router = new MessageRouter(pool);
        ServerNetworkServiceBuilder builder = new ServerNetworkServiceBuilder();
        builder.setMessageAndHandler(pool);
        builder.setBossLoopGroupCount(bossLoopGroupCount);
        builder.setWorkerLoopGroupCount(workerLoopGroupCount);
        builder.setPort(8201);
        builder.setListener(new NetworkListener());
        builder.setConsumer(router);

        router.initRouter();


        // 创建网络服务
        netWork = (ServerNetworkService) builder.createService();

//        // 初始化数据库
//        DataCenter.init(option);

//        //初始化配置文件
//        ConfigDataManager.getInstance().init();

//        // 注册事件
//        EventRegister.registerPreparedListeners();

//        //开启定时任务
//        ScheduleManager.getInstance().start();
    }

    public MessageRouter getRouter() {
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

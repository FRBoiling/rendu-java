package server.gameserver;

import core.network.server.ServerNetworkService;
import core.network.server.ServerNetworkServiceBuilder;
import lombok.Data;
import core.network.NetworkListener;
import server.ServerOption;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 15:13
 */
@Data
public class GameServer {
    private ServerNetworkService netWork;

    //tcp 连接状态
    private boolean state = false;
    //消息处理分发
    private GameServerMessageRouter router;

    public GameServer(ServerOption option) throws Exception {
        int bossLoopGroupCount = 4;
        int workerLoopGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8
                : Runtime.getRuntime().availableProcessors();

        GameServerMessageAndHandler pool = new GameServerMessageAndHandler();

        router = new GameServerMessageRouter(pool);
        router.initRouter();

        ServerNetworkServiceBuilder builder = new ServerNetworkServiceBuilder();
        builder.setMessageAndHandler(pool);
        builder.setBossLoopGroupCount(bossLoopGroupCount);
        builder.setWorkerLoopGroupCount(workerLoopGroupCount);
        builder.setPort(8201);
        builder.setListener(new NetworkListener());
        builder.setConsumer(router);

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

    public GameServerMessageRouter getRouter() {
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

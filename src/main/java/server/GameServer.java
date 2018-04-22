package server;

import core.network.server.ServerNetworkService;
import lombok.Data;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: server
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/23 0023 1:18
 * @version: V1.0
 */
@Data
public class GameServer {
    private ServerNetworkService netWork;

    private boolean state = false;

    private MessageRouter router;

    public GameServer(ServerOption option) throws Exception {
        int bossLoopGroupCount = 4;
        int workerLoopGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8
                : Runtime.getRuntime().availableProcessors();

        GameMessageAndHandler pool = new GameMessageAndHandler();

        router = new MessageRouter(pool);
        NetworkServiceBuilder builder = new NetworkServiceBuilder();
        builder.setImessageandhandler(pool);
        builder.setBossLoopGroupCount(bossLoopGroupCount);
        builder.setWorkerLoopGroupCount(workerLoopGroupCount);
        builder.setPort(option.getGameServerPort());
        builder.setListener(new NetworkListener());
        builder.setConsumer(router);

        //登录和下线
        router.registerProcessor(GameConst.QueueId.LOGIN_LOGOUT, new LoginProcessor());
        //业务队列
        router.registerProcessor(GameConst.QueueId.LOGIC, new LogicProcessor());

        // 创建网络服务
        netWork = builder.createService();

        // 初始化数据库
        DataCenter.init(option);

        //初始化配置文件
        ConfigDataManager.getInstance().init();

        // 注册事件
        EventRegister.registerPreparedListeners();

        //开启定时任务
        ScheduleManager.getInstance().start();
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

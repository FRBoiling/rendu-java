package server;

import Protocol.Client.Gate.C2GIdGenerater;
import Protocol.MsgId.Id;
import core.network.server.ServerNetworkService;
import core.network.server.ServerNetworkServiceBuilder;
import lombok.Data;
import server.constant.GameConst;
import server.processor.LogicProcessor;

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

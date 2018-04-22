package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: server
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/23 0023 1:10
 * @version: V1.0
 */
public class GameServerBootstrap {
    public static final Logger LOGGER = LoggerFactory.getLogger(GameServerBootstrap.class);

    public static void main(String[] args) throws Exception {
       String configPath = "config.properties";
//        if (args.length > 0) {
//            configPath = args[0];
//        }
        ServerOption option = new ServerOption(configPath);
        GameContext.init(option);

        GameServer server = GameContext.createGameServer();
        server.start();
        LOGGER.warn("游戏服务器启动成功...");

//        BackServer backServer = GameContext.createBackServer();
//        backServer.start();
//        LOGGER.warn("后台服务器启动成功...");

    }

}

package server;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-22
 * Time: 21:31
 */
@Slf4j
public class GameServerBootstrap {
//    public static final Logger LOGGER = LoggerFactory.getLogger(GameServerBootstrap.class);

    public static void main(String[] args) throws Exception {
        String configPath = "config.properties";
//        if (args.length > 0) {
//            configPath = args[0];
//        }
        ServerOption option = new ServerOption(configPath);
        GameContext.init(option);

        GameServer server = GameContext.createGameServer();
        server.start();
//        LOGGER.warn("游戏服务器启动成功...");
        log.warn("游戏服务器启动成功...");

//        BackServer backServer = GameContext.createBackServer();
//        backServer.start();
//        LOGGER.warn("后台服务器启动成功...");

    }

}

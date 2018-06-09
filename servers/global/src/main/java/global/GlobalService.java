package global;

import global.gate.GateServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */

@Slf4j
public class GlobalService
{
    public static void main( String[] args )
    {
        String configPath = "config.properties";
//        if (args.length > 0) {
//            configPath = args[0];
//        }
//        ServerOption option = new ServerOption(configPath);
//        ServerServiceContext.init(option);

        GlobalServiceContext.Init();
        GateServer server = GlobalServiceContext.createGateServer();
        server.start();
        log.warn("GateServer启动成功...");

//        BackServer backServer = GameContext.createBackServer();
//        backServer.start();
//        LOGGER.warn("后台服务器启动成功...");
    }
}

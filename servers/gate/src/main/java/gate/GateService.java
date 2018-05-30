package gate;

import gate.global.GlobalServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GateService
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String configPath = "config.properties";
//        if (args.length > 0) {
//            configPath = args[0];
//        }
//        ServerOption option = new ServerOption(configPath);
//        ServerServiceContext.init(option);

        GlobalServer server = GateServiceContext.createGlobalServer();
        server.start();
        log.warn("GlobalClient启动成功...");
    }
}

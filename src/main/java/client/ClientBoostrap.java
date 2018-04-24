package client;

import lombok.extern.slf4j.Slf4j;
import server.ServerServiceContext;
import server.ServerOption;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 21:31
 */

@Slf4j
public class ClientBoostrap {
    //    public static final Logger LOGGER = LoggerFactory.getLogger(GameServerBootstrap.class);

    public static void main(String[] args) throws Exception {
        String configPath = "config.properties";
//        if (args.length > 0) {
//            configPath = args[0];
//        }
        ClientOption option = new ClientOption(configPath);
        ClientServiceContext.init(option);

        Client client = ClientServiceContext.createClient();
        client.start();
        log.warn("客户端启动成功...");
    }
}

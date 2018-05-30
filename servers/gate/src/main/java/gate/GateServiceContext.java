package gate;

import gate.global.GlobalServer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:59
 */

public class GateServiceContext {
    private static int serverId;
    private static GlobalServer globalServer;
    private static boolean closed;

    public static GlobalServer createGlobalServer() {
        try {
            globalServer = new GlobalServer();
            return globalServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

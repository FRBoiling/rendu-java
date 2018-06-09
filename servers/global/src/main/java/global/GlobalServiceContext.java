package global;

import core.base.model.AbstractServiceContext;
import global.gate.GateServer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 11:04
 */

public class GlobalServiceContext extends AbstractServiceContext {
    private static GateServer gateServer;
    private static boolean closed;

    public static GateServer createGateServer() {
        try {
            gateServer = new GateServer();
            return gateServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

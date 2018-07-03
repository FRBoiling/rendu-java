package gate.global;

import core.base.concurrent.QueueDriver;
import core.network.AbstractMsgRouter;
import core.network.IResponseHandlerManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 14:36
 */
@Slf4j
public class GlobalServerMsgRouter extends AbstractMsgRouter {
    public GlobalServerMsgRouter(IResponseHandlerManager responseHandlerManager) {
        super(responseHandlerManager);
    }
}

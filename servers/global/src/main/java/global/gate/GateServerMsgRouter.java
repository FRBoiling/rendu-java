package global.gate;
import core.base.concurrent.QueueDriver;
import core.network.AbstractMsgRouter;
import core.network.IResponseHandlerManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-05-30
 * Time: 9:57
 */
@Slf4j
public class GateServerMsgRouter extends AbstractMsgRouter {

    public GateServerMsgRouter(IResponseHandlerManager responseHandlerManager) {
        super(responseHandlerManager);
    }
}

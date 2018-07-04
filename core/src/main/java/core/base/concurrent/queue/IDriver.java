package core.base.concurrent.queue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-03
 * Time: 20:53
 */

public interface IDriver {
    boolean addAction(IQueueDriverAction action);
    IMessageQueue<IQueueDriverAction> getActions();
}

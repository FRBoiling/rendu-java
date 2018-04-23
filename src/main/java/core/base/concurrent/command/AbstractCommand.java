package core.base.concurrent.command;

import core.base.concurrent.queue.ICommandQueue;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 14:21
 */
@Data
public abstract class AbstractCommand implements IQueueDriverCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommand.class);

    private ICommandQueue<IQueueDriverCommand> commandQueue;

    /**
     * 消息所属队列ID
     */
    protected int queueId;

//    @Override
//    public Object getParam() {
//        return null;
//    }
//
//    @Override
//    public void setParam(Object param) {
//
//    }
}

package servers.server.processor;

import core.base.common.IProcessor;
import core.base.concurrent.command.IQueueDriverCommand;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-04-23
 * Time: 15:13
 */
public class LogicProcessor implements IProcessor {
    private ExecutorService executor = new ThreadPoolExecutor(8, 8, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100000),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void process(IQueueDriverCommand handler) {
        this.executor.execute(handler);
    }
}

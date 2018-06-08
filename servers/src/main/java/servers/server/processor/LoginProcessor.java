package servers.server.processor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: FReedom
 * Date: 2018-05-11
 * Time: 14:22
 */


import core.base.common.IProcessor;
import core.base.concurrent.queue.IQueueDriverAction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 登录消息处理器
 *
 * @author Administrator
 */
public class LoginProcessor implements IProcessor {
    private ExecutorService executor = new ThreadPoolExecutor(8, 8, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100000),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void process(IQueueDriverAction handler) {
        this.executor.execute(handler);
    }
}

package global;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello zone!
 */

@Slf4j
public class GlobalService {
    public static Context context;

    public static void main(String[] args) {
        context = new Context();
        try {
            context.init(args);
            context.start();
            log.info("GlobalService启动成功...");
        } catch (Exception e) {
            context.stop();
        }
    }
}

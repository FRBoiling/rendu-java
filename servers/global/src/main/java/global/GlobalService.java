package global;

import global.gate.GateServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello zone!
 *
 */

@Slf4j
public class GlobalService
{
    static GlobalServiceContext context;
    public static void main( String[] args )
    {
        context = new GlobalServiceContext();
        try {
            context.init(args);
            context.start();
            log.info("GlobalService启动成功...");
        }catch (Exception e){
            context.stop();
        }
    }
}

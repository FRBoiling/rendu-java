package global;

import global.gate.GateServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
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
            log.warn("GlobalService启动成功...");
        }catch (Exception e){
            context.stop();
        }
    }
}

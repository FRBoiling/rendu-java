package gate;

import gate.global.GlobalServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GateService
{
    public static GateServiceContext context;
    public static void main( String[] args )
    {
        context = new GateServiceContext();
        try {
            context.init(args);
            context.start();
            log.info("GateService启动成功...");
        }catch (Exception e){
            context.stop();
        }
    }
}

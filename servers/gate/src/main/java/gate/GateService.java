package gate;

import gate.global.GlobalServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GateService
{
    static GateServiceContext context;
    public static void main( String[] args )
    {
        context = new GateServiceContext();
        context.init(args);
        context.start();
        log.info("GateService启动成功...");
    }
}

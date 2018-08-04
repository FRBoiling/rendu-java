package gate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GateService
{
    public static Context context;

    public static void main( String[] args )
    {
        context = new Context();
        try {
            context.init(args);
            context.start();
            log.info("GateService启动成功...");
        }catch (Exception e){
            context.stop();
        }
    }
}

package barrack;

import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-13
 * Time: 09:59
 */

@Slf4j
public class BarrackService
{
    public static Context context;
    public static void main( String[] args )
    {
        context = new Context();
        try {
            context.init(args);
            context.start();
            log.info("BarrackService启动成功...");
        }catch (Exception e){
            context.stop();
        }
    }
}

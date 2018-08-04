package manager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManagerService
{
    public static Context context;
    public static void main( String[] args )
    {
        context = new Context();
        context.init(args);
        context.start();
        log.info("ManagerService启动成功...");
    }
}

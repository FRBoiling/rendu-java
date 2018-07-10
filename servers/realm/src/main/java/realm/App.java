package realm;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello zone!
 *
 */
@Slf4j
public class App
{
//    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        log.trace("logback的--trace日志--输出了");
        log.debug("logback的--debug日志--输出了");
        log.info("logback的--info日志--输出了");
        log.warn("logback的--warn日志--输出了");
        log.error("logback的--error日志--输出了");
    }
}

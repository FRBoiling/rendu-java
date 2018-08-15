import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * WANART COMPANY
 * CreatedTime : 2018/8/13 13:53
 * CTEATED BY : JIANGYUNHUI
 */

public class TestLog {
    //private static final Logger log=LoggerFactory.getLogger(TestLog.class);

    public static volatile int canRun=1;

    @Test
    public void testlogs(){
        Logger log=LoggerFactory.getLogger(TestLog.class);
        LinkedList<Runnable> ts=new LinkedList<>();
        for(int i=0;i<2;i++){
            ts.add(new TestLogThread());
        }

        for (Runnable r:
             ts) {
            Thread t=new Thread(r);
            t.start();
        }
//        while(true){
//            try {
//                Thread.currentThread().sleep(1L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("this is the test from jiang info {}",Thread.currentThread().getId());
//            //log.error("this is the test from jiang error");
//            //log.trace("this is trace");
//            //log.warn("this is warn");
//            //log.debug("this is debug in test logs ");
//        }
        while(true){
            try {
                Thread.currentThread().sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Scanner sc=new Scanner(System.in);
        //canRun=Boolean.getBoolean(sc.nextLine());

    }

}

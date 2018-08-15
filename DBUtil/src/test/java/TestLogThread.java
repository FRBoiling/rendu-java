import lombok.extern.slf4j.Slf4j;

/**
 * WANART COMPANY
 * CreatedTime : 2018/8/13 18:05
 * CTEATED BY : JIANGYUNHUI
 */
@Slf4j
public class TestLogThread implements Runnable{


    @Override
    public void run() {
        //log.info("this is the test from jiang info {} ", Thread.currentThread().getId());
        //System.out.println("this is the system.out "+Thread.currentThread().getId());
        while(true) {
            log.info("this is the test from jiang info {} ", Thread.currentThread().getId());
            log.debug("this is the test from jiang info {} ", Thread.currentThread().getId());
            log.error("this is the test from jiang info {} ", Thread.currentThread().getId());
            try {
                Thread.currentThread().sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

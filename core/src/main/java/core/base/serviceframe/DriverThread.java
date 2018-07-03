package core.base.serviceframe;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-03
 * Time: 17:55
 */

/**
 * 主线程驱动
 */
public class DriverThread extends Thread {
    private String threadName;
    IService service;
    public DriverThread(String name, IService service) {
        this.service =service;
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    @Override
    public void run() {
        service.update();
    }
}

package core.base.serviceframe;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-07-03
 * Time: 17:55
 */

import lombok.extern.slf4j.Slf4j;

/**
 * 主线程驱动
 */
@Slf4j
public class DriverThread extends Thread {
    private IService service;
    DriverThread(String name, IService service) {
        this.service =service;
        this.setName(name+"-"+getId());;
        log.info("Creating " +  getName() );
    }

    @Override
    public void run() {
        service.update(1);
    }
}

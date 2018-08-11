package core.base.serviceframe;

import gamedb.DBManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-10
 * Time: 17:43
 **/
@Slf4j
public class DBDriverThread extends Thread {
    private DBManager db;
    public DBDriverThread(String name, DBManager db) {
        this.db = db;
        this.setName(name+"-"+getId());;
        log.info("Creating " +  getName() );
    }

    @Override
    public void run() {
        db.run();
    }
}

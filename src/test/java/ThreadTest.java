/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: PACKAGE_NAME
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 22:54
 * @version: V1.0
 */
public class ThreadTest extends Thread {
    int things = 5;
    @Override
    public void run() {
        while (things > 0) {
            System.out.println(currentThread().getName() + " things:" + things);
            things--;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        ThreadTest thread1 = new ThreadTest();
        ThreadTest thread2 = new ThreadTest();
        ThreadTest thread3 = new ThreadTest();
        thread1.start();
        thread2.start();
        thread3.start();
    }
}



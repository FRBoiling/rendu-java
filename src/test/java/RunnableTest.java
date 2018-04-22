/**
 * Copyright © 2018 四月
 * Boil blood. All rights reserved.
 *
 * @Prject: ServerCluster-Java
 * @Package: PACKAGE_NAME
 * @Description: ${todo}
 * @author: Boiling
 * @date: 2018/4/22 0022 22:58
 * @version: V1.0
 */
public class RunnableTest implements Runnable {
    String name;

    public RunnableTest(String name) {
        this.name = name;
    }

    int things = 5;

    @Override
    public void run() {
        while (things > 0) {
            things--;
            System.out.println(name + " things:" + things);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        RunnableTest run = new RunnableTest("run");
        Thread th1 = new Thread(run, "Thread 1");
        Thread th2 = new Thread(run, "Thread 2");
        Thread th3 = new Thread(run, "Thread 3");

        th1.start();
        th2.start();
        th3.start();
    }
}

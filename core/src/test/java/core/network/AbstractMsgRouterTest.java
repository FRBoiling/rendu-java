package core.network; 

import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

/** 
* AbstractMsgRouter Tester. 
* 
* @author <Authors name> 
* @since <pre>07/03/2018</pre> 
* @version 1.0 
*/ 
public class AbstractMsgRouterTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: consume(Packet packet, Channel channel) 
* 
*/ 
@Test
public void testConsume() throws Exception {
//    QueueExecutor queueExecutor = new QueueExecutor("test.executor", 1, 3);
//    QueueDriver queueDriver = new QueueDriver(queueExecutor, "test.driver", 1, 1024);
//
//    for (int i = 0; i < 1000; i++) {
//        final int index = i;
//        new MutliThread(index, queueDriver).start();
//    }
//    while (true) {
//        Thread.sleep(1000);
//    }
//}
//    class MutliThread extends Thread{
//        QueueDriver queueDriver;
//        int id;
//        MutliThread(Integer id,QueueDriver driver){
//            super("test thread"+id);//调用父类带参数的构造方法
//            this.id=id;
//            queueDriver =driver;
//        }
//        public void run(){
//                Thread t = Thread.currentThread();
//                String name = t.getName();
////                AbstractHandler handler =new TestHandler();
////                handler.setParam(id);
////                queueDriver.addAction(handler);
////                System.out.println("AbstractMsgRouter cur thread name=" + name);
//        }
    }
}

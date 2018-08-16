package core.base.common;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * AbstractSessionManager Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>$Today</pre>
 */
public class AbstractSessionManagerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: initRegister(AbstractSession session)
     */
    @Test
    public void testRegister() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: update(long dt)
     */
    @Test
    public void testUpdate() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: updateLogic(long dt)
     */
    @Test
    public void testUpdateLogic() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: createSession(Channel channel)
     */
    @Test
    public void testCreateSession() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: putSession(AbstractSession session)
     */
    @Test
    public void testPutSession() throws Exception {
        ConcurrentHashMap<Integer, Integer> hashMap = new ConcurrentHashMap<>(16);
        Set<Integer> set = new HashSet();
        for (int i = 0; i < 10000; i++) {
            hashMap.put(i, i);
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            hashMap.remove(key);
            Integer a = value;
            set.add(a);
            if (!value.equals(key) || !a.equals(key)) {
                    System.out.println("error!!!!!!!!");
            }
        }
        for (Integer v:set) {
            if(v == null){
                break;
            }else {
                System.out.println("--->"+v);
            }
        }
        System.out.println("end");
//TODO: Test goes here...
    }

    /**
     * Method: removeSession(AbstractSession session)
     */
    @Test
    public void testRemoveSession() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getRegisterSessions()
     */
    @Test
    public void testGetRegisterSessions() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getRegisterSession(ISessionTag tag)
     */
    @Test
    public void testGetRegisterSession() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: broadcastAll(MessageLite msg)
     */
    @Test
    public void testBroadcastAll() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: broadcastAllExceptServer(MessageLite msg, ServerTag tag)
     */
    @Test
    public void testBroadcastAllExceptServer() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: broadcastByArea(MessageLite msg, int areaId)
     */
    @Test
    public void testBroadcastByGroup() throws Exception {
//TODO: Test goes here...
        HashMap<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            hashMap.put("key" + i, "value" + i);
        }
        long time1 = method1(hashMap);
        long time2 = method2(hashMap);
        long time3 = method3(hashMap);
        System.out.println("1：" + time1);
        System.out.println("2：" + time2);
        System.out.println("3：" + time3);
    }

    private long method1(HashMap<String, String> hashMap) {
        String tmp;
        long startTime = System.currentTimeMillis();
        for (String str : hashMap.values()) {
            tmp = str;
            System.out.println(tmp);
        }
        return System.currentTimeMillis() - startTime;
    }

    private long method2(HashMap<String, String> hashMap) {
        String tmp;
        long startTime = System.currentTimeMillis();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            tmp = entry.getValue();
            System.out.println(tmp);
        }
        return System.currentTimeMillis() - startTime;
    }

    private long method3(HashMap<String, String> hashMap) {
        String tmp;
        long startTime = System.currentTimeMillis();
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            tmp = (String) entry.getValue();
            System.out.println(tmp);
        }
        return System.currentTimeMillis() - startTime;
    }


    /**
     * Method: broadcastByAreaExceptServer(MessageLite msg, int areaId, ServerTag tag)
     */
    @Test
    public void testBroadcastByGroupExceptServer() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: send2Session(ISessionTag sessionTag, MessageLite msg)
     */
    @Test
    public void testSend2Session() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: unregister(AbstractSession session)
     */
    @Test
    public void testUnregister() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = AbstractSessionManager.getClass().getMethod("unregister", AbstractSession.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: addConnectingSession()
     */
    @Test
    public void testAddConnectingSession() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = AbstractSessionManager.getClass().getMethod("addConnectingSession"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 

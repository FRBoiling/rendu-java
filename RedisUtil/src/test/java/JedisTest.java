import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class JedisTest {

    @Test
    public void testCluster(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(1);
        poolConfig.setMaxTotal(1);
        poolConfig.setMaxWaitMillis(1000);

        Set<HostAndPort> nodes=new LinkedHashSet<>();
        nodes.add(new HostAndPort("192.168.30.245", 6380));
        JedisCluster cluster=new JedisCluster(nodes,poolConfig);

        for(int i=0;i<1000;i++){
            //cluster.set("jiangTest","test"+i);
            cluster.hset("jiangTest"+i, "test",""+i);
        }


        try {
            cluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(cluster.get("name")+"test from jedis test");
        System.out.println("cluster nodes"+nodes.size());
    }
    @Test
    public void demo1() {

        Jedis jedis=new Jedis("192.168.30.245",6380);

        //切换数据库
        //jedis.select(0);

        //HashSet举例
        //jedis.hset("P:10:H:1000:SD", "Fight7Day_4","13");
        //jedis.hset("P:10:H:1000:SD", "Fight7Day_5","14");

        //SortedSet
        //jedis.zadd("OS", 1566072532612.0,"1" );

        //set
        //jedis.sadd("Geo:wx4a","82","2","3");
        //jedis.srem("Geo:wx4e",new String[]{"82"});

        //list
        //jedis.lpush( "P:10:GiR","{0}:{1}:{2}:{3}");

        //string
        jedis.set("jiangTest","test1");

        jedis.close();

    }

    @Test
    public void demo2(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(10);

        JedisPool pool=new JedisPool(config,"127.0.0.1",6379);
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.set("name","李四");

            String value=jedis.get("name");
            System.out.println(value);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }

            if(pool!=null){
                pool.close();
            }
        }
    }

    @Test
    public void testGC(){
        int count=0;
        while (true){
            count++;
            Byte[] test=new Byte[1024];
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count%10==0) {
                System.gc();
                System.out.println("test GC");
            }
        }
    }
}

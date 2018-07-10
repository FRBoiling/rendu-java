import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
    @Test
    public void demo1() {

        Jedis jedis=new Jedis("127.0.0.1",6379);

        //切换数据库
        jedis.select(0);

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
        //jedis.set("ahaaas","adfasdsa");

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

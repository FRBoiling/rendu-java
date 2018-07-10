package basic;

import redis.clients.jedis.*;

import java.util.logging.Logger;

public class RedisManager {
    private static RedisManager _instance;
    public JedisConfig jedisConfig=null;
    private static final Object Locker = new Object();

    public static RedisManager getInstance(){
        if (_instance == null)
        {
            synchronized (Locker)
            {
                if (_instance == null )
                {
                    _instance = new RedisManager();
                }
            }
        }
        return _instance;
    }

    private RedisManager(){}

    private JedisPool pool;

    public JedisPool initPool(JedisConfig config,int poolMax,int poolIdle){
        jedisConfig=config;
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(poolMax);
        poolConfig.setMaxIdle(poolIdle);

        pool=new JedisPool(poolConfig,config.getIp(),config.getPort());
        return pool;
    }

    public JedisPool getPool(){
        if(pool==null){
            //todo
            //打印log提示没有初始化,临时
            System.out.println("jedis pool not inited");
        }
        return pool;
    }
}

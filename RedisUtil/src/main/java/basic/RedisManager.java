package basic;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.LinkedHashSet;
import java.util.Set;
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

    //private JedisPool pool;

    private JedisCluster cluster=null;

    public JedisCluster initPool(JedisConfig config,int poolMax,int poolIdle){
        jedisConfig=config;
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(poolMax);
        poolConfig.setMaxIdle(poolIdle);

        Set<HostAndPort> nodes=new LinkedHashSet<>();
        nodes.add(new HostAndPort(config.getIp(),config.getPort()));
        cluster=new JedisCluster(nodes,config.getConnectionTimeout(),config.getSoTimeout(),config.getAttempts(),config.getPassword(),poolConfig);
        //cluster=new JedisCluster(nodes,poolConfig);

        return cluster;
    }

    public JedisCluster getCluster(){
        if(cluster==null){
            //todo
            //打印log提示没有初始化,临时
            System.out.println("jedis pool not inited");
        }
        return cluster;
    }
}

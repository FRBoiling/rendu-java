package basic;

import basicCallBack.ObjectBeCalled;
import operate.AbstractRedisOperate;

import java.util.ArrayList;

public class RedisManagerPool {
    private int poolCount=0;
    private int index=0;

    private ArrayList<RedisQueueManager> redisQueryList=new ArrayList<>();
    public ArrayList<RedisQueueManager> getRedisQueryList() {
        return redisQueryList;
    }

    private ArrayList<Thread> redisThreadList=new ArrayList<>();

    public RedisManagerPool(int count){
        poolCount=count;
        for(int i=0;i<poolCount;i++){
            RedisQueueManager redis=new RedisQueueManager();
            redis.init();
            redisQueryList.add(redis);
        }
    }

    public boolean init()
    {
        for (RedisQueueManager redis :redisQueryList)
        {
            Thread redisThread = new Thread(()->redis.Run());
            redisThreadList.add(redisThread);
            redisThread.start();
        }
        return true;
    }

    public void abort(){
        for(Thread thread: redisThreadList){
            thread.interrupt();
        }
        redisThreadList.clear();
    }

    public boolean exit(){
        for(RedisQueueManager que : redisQueryList){
            que.Exit();
        }
        return true;
    }

    public int call(AbstractRedisOperate query, ObjectBeCalled callBack){
        int redisIndex=getRedisIndex();
        redisQueryList.get(redisIndex).Call(query,callBack);
        return redisIndex;
    }

    public int getRedisIndex(){
        index++;
        if(index>=10000){
            index=0;
        }
        return index%redisQueryList.size();
    }
}

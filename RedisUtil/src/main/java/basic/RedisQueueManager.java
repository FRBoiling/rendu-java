package basic;

import basicCallBack.ObjectBeCalled;
import operate.*;
import util.Time;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RedisQueueManager {
    private ConcurrentLinkedQueue<AbstractRedisOperate> saveQueue = new ConcurrentLinkedQueue<AbstractRedisOperate>();

    private ConcurrentLinkedQueue<AbstractRedisOperate> executionQue;
    private ConcurrentLinkedQueue<AbstractRedisOperate> postUpdateQue;

    private ConcurrentLinkedQueue<String> exceptionLogQueue;

    public boolean Opened=false;

    public void init(){
        saveQueue=new ConcurrentLinkedQueue<>();
        executionQue=new ConcurrentLinkedQueue<>();
        postUpdateQue=new ConcurrentLinkedQueue<>();
        exceptionLogQueue=new ConcurrentLinkedQueue<>();
        Opened=true;
    }

    public boolean Exit(){
        Opened=false;
        return true;
    }
    public void Add(AbstractRedisOperate query){
        query.Init();
        saveQueue.offer(query);
    }

    public void Call(AbstractRedisOperate query){
        Add(query);
    }

    public long lasttime;
    long totaltime;

    public void Wait(long timeSpan){
        try {
            Thread.sleep(timeSpan);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Run() {
        Time time=new Time();
        time.init();
        ConcurrentLinkedQueue<AbstractRedisOperate> tempPostUpdateQueue=new ConcurrentLinkedQueue<>();
        while(true){
            long timeSpan=time.init();
            lasttime=timeSpan;
            if(lasttime<=1){
                Wait(1L);
            }
            if(totaltime>10000L){
                totaltime=0;
            }else{
                totaltime+=lasttime;
            }

            try{
                if(saveQueue.isEmpty()){
                    continue;
                }
                while (!saveQueue.isEmpty()){
                    AbstractRedisOperate query= saveQueue.poll();
                    executionQue.offer(query);
                }

                while(!executionQue.isEmpty()){
                    AbstractRedisOperate query=executionQue.poll();
                    boolean success=query.execute();
                    if(success==false){
                        if(query.ErrorText!=null){
                            AddExceptionLog(query.ErrorText);
                        }
                    }
                    tempPostUpdateQueue.offer(query);
                }

                while (!tempPostUpdateQueue.isEmpty()){
                    postUpdateQue.offer(tempPostUpdateQueue.poll());
                }

                tempPostUpdateQueue.clear();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ConcurrentLinkedQueue<AbstractRedisOperate> GetPostUpdateQueue(){
        ConcurrentLinkedQueue<AbstractRedisOperate> ret=new ConcurrentLinkedQueue<>();
        while(!postUpdateQue.isEmpty()){
            AbstractRedisOperate query=postUpdateQue.poll();
            ret.offer(query);
        }
        return ret;
    }

    public ConcurrentLinkedQueue<String> GetExceptionLogQueue(){
        ConcurrentLinkedQueue<String> ret;
        if(!exceptionLogQueue.isEmpty()){
            ret=exceptionLogQueue;
            exceptionLogQueue=new ConcurrentLinkedQueue<>();
        }else{
            return null;
        }
        return ret;
    }

    public void AddExceptionLog(String log){
        exceptionLogQueue.offer(log);
    }

    public void Call(AbstractRedisOperate query, ObjectBeCalled callBack) {
        query.RegistCallBack(callBack);
        Add(query);
    }
}

package gamedb;

import basicCallBack.ObjectBeCalled;
import util.Time;
import gamedb.dao.AbstractDBOperator;

import java.util.concurrent.ConcurrentLinkedQueue;


public class DBManager {

    private ConcurrentLinkedQueue<AbstractDBOperator> saveQueue = new ConcurrentLinkedQueue<AbstractDBOperator>();

    private ConcurrentLinkedQueue<AbstractDBOperator> executionQue;
    private ConcurrentLinkedQueue<AbstractDBOperator> postUpdateQue;

    private ConcurrentLinkedQueue<String> exceptionLogQueue;

    public boolean Opened=false;

    public boolean init(){
        saveQueue=new ConcurrentLinkedQueue<>();
        executionQue=new ConcurrentLinkedQueue<>();
        postUpdateQue=new ConcurrentLinkedQueue<>();
        exceptionLogQueue=new ConcurrentLinkedQueue<>();
        Opened=true;
        return true;
    }

    public boolean Exit(){
        Opened=false;
        return true;
    }
    public void Add(AbstractDBOperator query){
        query.Init(this);
        saveQueue.offer(query);
    }

    public void Call(AbstractDBOperator query){
        Add(query);
    }

    public void Call(AbstractDBOperator query, ObjectBeCalled callee){
        query.Init(this);
        query.RegistCallBack(callee);
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
        time.Init();
        ConcurrentLinkedQueue<AbstractDBOperator> tempPostUpdateQueue=new ConcurrentLinkedQueue<>();
        while(true){
            long timeSpan=time.Update();
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
                    AbstractDBOperator query= saveQueue.poll();
                    executionQue.offer(query);
                }

                while(!executionQue.isEmpty()){
                    AbstractDBOperator query=executionQue.poll();
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

    public ConcurrentLinkedQueue<AbstractDBOperator> GetPostUpdateQueue(){
        ConcurrentLinkedQueue<AbstractDBOperator> ret=new ConcurrentLinkedQueue<>();
        while(!postUpdateQue.isEmpty()){
            AbstractDBOperator query=postUpdateQue.poll();
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


}

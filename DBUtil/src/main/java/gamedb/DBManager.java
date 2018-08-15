package gamedb;

import basicCallBack.ObjectBeCalled;
import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.CheckDBOperator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import util.FileUtil;
import util.Time;
import gamedb.dao.AbstractDBOperator;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class DBManager {

    private ConcurrentLinkedQueue<AbstractDBOperator> saveQueue = new ConcurrentLinkedQueue<AbstractDBOperator>();

    private Queue<AbstractDBOperator> executionQue;
    private ConcurrentLinkedQueue<AbstractDBOperator> postUpdateQue;

    private ConcurrentLinkedQueue<String> exceptionLogQueue;

    public boolean Opened = false;

    @Getter
    @Setter
    private static volatile boolean haveConnection = true;



    public boolean init() {
        saveQueue = new ConcurrentLinkedQueue<>();
        executionQue = new ArrayDeque<>();
        postUpdateQue = new ConcurrentLinkedQueue<>();
        exceptionLogQueue = new ConcurrentLinkedQueue<>();
        Opened = true;
        return true;
    }

    public void initConfig(String configPath){
        //初始化Mybatis配置
        List<File> fileList = new ArrayList<>();
        FileUtil.findFiles(configPath, "mybatis_config.xml", fileList);
        if (fileList.size() == 0) {
            FileUtil.findFiles(System.getProperty("user.dir"), "mybatis_config.xml", fileList);
        }
        if (fileList.size() > 0) {
            for (File file : fileList) {
                MybatisConfigUtil.InitWithFile(file);
                MybatisConfigUtil.checkConnections(this);
                log.info("-------------- Mybatis Config Done---------------");
                break;
            }
        } else {
            log.error("--------------no mybatis_config.xml---------------");
        }
    }



    public boolean Exit() {
        Opened = false;
        return true;
    }

    public void Add(AbstractDBOperator query) {
        query.Init(this);
        saveQueue.offer(query);
    }

    public void Call(AbstractDBOperator query) {
        Add(query);
    }

    public void Call(AbstractDBOperator query, ObjectBeCalled callee) {
        query.Init(this);
        query.RegistCallBack(callee);
        Add(query);
    }

    public long lasttime;
    long totaltime;

    public void Wait(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Time time = new Time();
        time.init();
        Queue<AbstractDBOperator> tempPostUpdateQueue = new ArrayDeque<>();
        while (true) {
            lasttime = time.update();
            if (lasttime <= 1) {
                Wait(1L);
            }
            if (totaltime > 10000L) {
                totaltime = 0;
            } else {
                totaltime += lasttime;
            }

            try {
                if (saveQueue.isEmpty()) {
                    continue;
                }
                while (!saveQueue.isEmpty()) {
                    AbstractDBOperator query = saveQueue.poll();
                    executionQue.offer(query);
                }

                while (!executionQue.isEmpty()) {
                    AbstractDBOperator query = executionQue.poll();
                    boolean success = query.execute();
                    if (!success) {
                        if (query.ErrorText != null) {
                            AddExceptionLog(query.ErrorText);
                        }
                    }
                    tempPostUpdateQueue.offer(query);
                }

                while (!tempPostUpdateQueue.isEmpty()) {
                    postUpdateQue.offer(tempPostUpdateQueue.poll());
                }

                tempPostUpdateQueue.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Queue<AbstractDBOperator> GetPostUpdateQueue() {
        Queue<AbstractDBOperator> ret = new ArrayDeque<>();
        while (!postUpdateQue.isEmpty()) {
            AbstractDBOperator query = postUpdateQue.poll();
            ret.offer(query);
        }
        return ret;
    }

    public Queue<String> GetExceptionLogQueue() {
        Queue<String> ret; ;
        if (!exceptionLogQueue.isEmpty()) {
            ret= new ArrayDeque<>(exceptionLogQueue);
            exceptionLogQueue.clear();
        } else {
            return null;
        }
        return ret;
    }

    public void AddExceptionLog(String log) {
        exceptionLogQueue.offer(log);
    }


}

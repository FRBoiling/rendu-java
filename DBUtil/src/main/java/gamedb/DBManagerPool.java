package gamedb;

import basicCallBack.ObjectBeCalled;
import gamedb.dao.AbstractDBOperator;

import java.util.*;

public class DBManagerPool {
    private int poolCount=0;
    private int index = 0;

    public ArrayList<DBManager> getDbManagerList() {
        return dbManagerList;
    }

    private ArrayList<DBManager> dbManagerList = new ArrayList<>();

    private LinkedList<Thread> dbThreadList = new LinkedList<>();
    public HashMap<Integer, Integer> DBCallCountList = new HashMap<>();
    public HashMap<String, Integer> DBCallNameList = new HashMap<>();

    private String ip;
    private String dbName;
    private String dbAccount;
    private String password;
    private String port;

    
    public DBManagerPool(int count)
    {
        poolCount = count;
        for (int i = 0; i < poolCount; i++)
        {
            DBManager db = new DBManager();
            dbManagerList.add(db);
            DBCallCountList.put(i,0);
        }
    }

    public boolean Init(String ip, String database, String id, String pw, String port)
    {
        //此处配置无用，需在mybatis配置
//        this.ip = ip;
//        dbName = database;
//        dbAccount = id;
//        password = pw;
//        this.port = port;
        return Init();
    }

    public boolean Init()
    {
        for(DBManager db:dbManagerList)
        {
            if (db.init() == false)
            {
                return false;
            }
            Thread dbThread = new Thread(()->db.Run());
            dbThreadList.add(dbThread);
            dbThread.start();
        }
        return true;
    }

    public int Call(AbstractDBOperator query, ObjectBeCalled callback)
    {
        int dbIndex = GetDBIndex();
        dbManagerList.get(dbIndex).Call(query, callback);
        return dbIndex;
    }

    public int Call(AbstractDBOperator query, int force_index, ObjectBeCalled callback)
    {
        int dbIndex = 0;
        if (force_index > 0 && force_index < dbManagerList.size())
        {
            dbManagerList.get(force_index).Call(query, callback);
            Integer count=DBCallCountList.get(force_index);
            count++;
        }
        else
        {
            dbManagerList.get(0).Call(query, callback);
            Integer count=DBCallCountList.get(0);
            count++;
        }
        if (DBCallNameList.containsKey(query.toString()) == false)
        {
            DBCallNameList.put(query.toString(), 1);
        }
        else
        {
            Integer count=DBCallNameList.get(query.toString());
            count++;
        }
        return dbIndex;
    }

    public int GetNextDBIndex()
    {
        return ((index + 1 )% dbManagerList.size());
    }

    public int GetDBIndex()
    {
        index++;
        if(index >= 10000)
        {
            index = 0;
        }
        return index % dbManagerList.size();
    }

    public void Abort()
    {
        for (Thread thread:dbThreadList)
        {
            thread.interrupt();
        }
        dbThreadList.clear();
    }

    public boolean Exit()
    {
        for (DBManager db:dbManagerList)
        {
            try
            {
                db.Exit();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                return false;
            }
        }
        return true;
    }

    public DBManager GetOneDBManager()
    {
        int curIndex = GetDBIndex();
        return dbManagerList.get(curIndex);
    }

}

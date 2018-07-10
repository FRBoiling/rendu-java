package gamedb;
import basicCallBack.*;
import gamedb.dao.*;
import java.util.*;

public class DBMngPoolManager {
    public HashMap<String, DBManagerPool> DBNameList = new HashMap<>();
    // key tableName, value DBManagerPool
    public HashMap<String, DBManagerPool> WriteTableList = new HashMap<>();
    public HashMap<String, DBManagerPool> ReadTableList = new HashMap<>();

    public void AddTableDb(String table_name, DBManagerPool db, DBOperateType operate_type)
    {
        switch (operate_type)
        {
            case Write:
                WriteTableList.put(table_name, db);
                break;
            case Read:
                ReadTableList.put(table_name, db);
                break;
            default:
                System.out.println("add table db failed: got invalid opetate type "+ operate_type);
                break;
        }
    }

    public void AddNameDb(String nick_name, DBManagerPool db)
    {
        DBNameList.put(nick_name, db);
    }
    public DBManagerPool GetDbByName(String nick_name)
    {
        DBManagerPool db = null;
        db=DBNameList.get(nick_name);
        return db;
    }

    public DBManagerPool GetWriteDbByName(String table_name)
    {
        DBManagerPool db = null;
        db=WriteTableList.get(table_name);
        return db;
    }

    public DBManagerPool GetReadDbByName(String table_name)
    {
        DBManagerPool db = null;
        db=ReadTableList.get(table_name);
        return db;
    }

    public DBManagerPool GetDbByTable(String table_name, DBOperateType type)
    {
        DBManagerPool db = null;
        switch (type)
        {
            case Write:
                db=WriteTableList.get(table_name);
                break;
            case Read:
                db=ReadTableList.get(table_name);
                break;
            default:
                break;
        }
        return db;
    }

    public void Abort()
    {
        for (String key: DBNameList.keySet())
        {
            try
            {
                DBManagerPool db=DBNameList.get(key);
                db.Abort();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
    }

    public boolean Exit()
    {
        for (String key: DBNameList.keySet())
        {
            try
            {
                DBManagerPool db=DBNameList.get(key);
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

    public void Call(AbstractDBOperator query, String table_name, DBOperateType type, ObjectBeCalled callback)
    {
        DBManagerPool dbPool = GetDbByTable(table_name, type);
        if (dbPool == null)
        {
            //Logger.Log.Warn("db call %s failed: can not find table %s type %s db", query.GetCmd(), table_name, type.ToString());
            return;
        }
        dbPool.Call(query, callback);
    }

    public void Call(AbstractDBOperator query,int index, String table_name, DBOperateType type, ObjectBeCalled callback)
    {
        DBManagerPool dbPool = GetDbByTable(table_name, type);
        if (dbPool == null)
        {
            //Logger.Log.Warn("db call %s failed: can not find table %s type %s db", query.GetCmd(), table_name, type.ToString());
            return;
        }
        dbPool.Call(query, index, callback);
    }

    public String GetTableName(String table_name, int param, DBTableParamType type)
    {
        switch (type)
        {
            case Character:
                int suffix = param % DBProxyDefault.TableBaseCount;
                if (suffix >= 10)
                {
                    table_name = String.format("%s_%s", table_name, suffix);
                }
                else
                {
                    table_name = String.format("%s_0%s", table_name, suffix);
                }
                break;
            default:
                //Logger.Log.Warn("get table name failed: invalid db table param type %s", type);
                break;
        }
        return table_name;
    }
    
}


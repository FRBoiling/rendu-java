package gamedb.dao;

import basicCallBack.ObjectBeCalled;
import basicCallBack.ObjectWithCallBack;
import gamedb.DBManager;
import org.apache.ibatis.session.SqlSession;

abstract public class AbstractDBOperator extends ObjectWithCallBack {

    protected DBManager dbManager;

    protected int m_result;

    public String ErrorText;

    protected SqlSession sqlSession=null;

    public void PostUpdate(){
        if (!callbacks.isEmpty()){
            for (ObjectBeCalled call:callbacks) {
                call.call(this);
            }
        }
    }

    public void Init(DBManager db_manager){
        this.dbManager=db_manager;
    }

    abstract public boolean execute();

    public int getResult() {
        return m_result;
    }
}

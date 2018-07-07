package gamedb.dao;

import basicCallBack.ObjectBeCalled;
import basicCallBack.ObjectWithCallBack;
import gamedb.DBManager;

abstract public class AbstractDBOperator extends ObjectWithCallBack {

    protected DBManager dbManager;

    protected Object m_result;

    public String ErrorText;

    public void PostUpdate(){
        if (!callbacks.isEmpty()){
            for (ObjectBeCalled call:callbacks) {
                call.call(this,arg);
            }
        }
    }

    public void Init(DBManager db_manager){
        this.dbManager=db_manager;
    }

    abstract public boolean execute();
}

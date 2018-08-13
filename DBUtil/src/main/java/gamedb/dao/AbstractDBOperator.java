package gamedb.dao;

import basicCallBack.ObjectBeCalled;
import basicCallBack.ObjectWithCallBack;
import gamedb.DBManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;

import static com.mysql.jdbc.SQLError.SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE;

@Slf4j
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

    protected void endDB(){
        log.error("db is ending");
        dbManager.setHaveConnection(false);
    }

    public void checkExCause(Exception ex){
        if(ex.getCause() instanceof SQLException){
            SQLException realEx=(SQLException)ex.getCause();
            if(realEx.getSQLState().equals(SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE)){
                endDB();
            }
        }
    }
}

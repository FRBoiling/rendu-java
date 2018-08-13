package gamedb.dao;

import gamedb.Util.MybatisConfigUtil;
import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.interfaces.AccountMapper;

import java.sql.SQLException;


/**
 * WANART COMPANY
 * CreatedTime : 2018/8/13 11:26
 * CTEATED BY : JIANGYUNHUI
 */

public class CheckDBOperator extends AbstractDBOperator {

    @Override
    public boolean execute() {

        try{
            sqlSession = MybatisConfigUtil.openSqlSession();
            //sqlSession = SqlSessionFactoryUtil.openSqlSession();
            sqlSession.getConnection();
            sqlSession.commit();
        }catch (Exception ex){
            checkExCause(ex);
            m_result=-1;
            return false;
        }finally {
            sqlSession.close();
            //m_result=0;
            return true;
        }
    }
}

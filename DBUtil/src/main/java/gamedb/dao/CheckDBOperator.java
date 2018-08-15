package gamedb.dao;

import gamedb.Util.MybatisConfigUtil;
import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.interfaces.AccountMapper;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;


/**
 * WANART COMPANY
 * CreatedTime : 2018/8/13 11:26
 * CTEATED BY : JIANGYUNHUI
 */

public class CheckDBOperator extends AbstractDBOperator {

    public CheckDBOperator(SqlSession sqlSession){
        this.sqlSession=sqlSession;
    }

    @Override
    public boolean execute() {

        try{
            //sqlSession = SqlSessionFactoryUtil.openUniversalSqlSession();
            //sqlSession = SqlSessionFactoryUtil.openSqlSession();
            sqlSession.getConnection();
            //sqlSession.getConfiguration().getEnvironment()
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

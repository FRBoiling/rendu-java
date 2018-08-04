package gamedb.dao.character;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.CharMapper;
import org.apache.ibatis.session.SqlSession;


public class MaxCharUidDBOperator extends AbstractDBOperator {

    String tableName;
    public int maxUid;
    public MaxCharUidDBOperator(String tableName){
        this.tableName=tableName;
    }
    @Override
    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            CharMapper charMapper=sqlSession.getMapper(CharMapper.class);
            //HashMap<String,String> map=new HashMap<>();
            maxUid=charMapper.getMaxUid(tableName);
            m_result = 1;
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            m_result=-1;
        }finally {
            sqlSession.close();
            return true;
        }

    }
}

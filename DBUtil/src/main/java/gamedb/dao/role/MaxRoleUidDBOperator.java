package gamedb.dao.role;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import org.apache.ibatis.session.SqlSession;


public class MaxRoleUidDBOperator extends AbstractDBOperator {

    public int maxUid;

    public MaxRoleUidDBOperator() {
    }

    @Override
    public boolean execute() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            RoleMapper charMapper = sqlSession.getMapper(RoleMapper.class);
            //HashMap<String,String> map=new HashMap<>();
            maxUid = charMapper.getMaxUid("role");
            m_result = 1;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            m_result = -1;
        } finally {
            sqlSession.close();
            return true;
        }

    }
}

package gamedb.dao.role;

import gamedb.Util.MybatisConfigUtil;
import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import gamedb.pojo.RolePOJO;
import org.apache.ibatis.session.SqlSession;

public class DeleteRoleDBOperator extends AbstractDBOperator {
    private RolePOJO role=null;

    public DeleteRoleDBOperator(Object arg, RolePOJO role) {
        this.role=role;
    }

    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = MybatisConfigUtil.openSqlSession();
            RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);

            int count=roleMapper.deleteRole(role);
            sqlSession.commit();
            System.err.println("DeleteRoleDBOperator execute count "+count);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            checkExCause(ex);
        }finally {
            sqlSession.close();
            return true;
        }

    }
}

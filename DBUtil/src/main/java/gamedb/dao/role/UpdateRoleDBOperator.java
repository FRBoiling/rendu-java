package gamedb.dao.role;

import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import gamedb.pojo.role.RolePOJO;
import org.apache.ibatis.session.SqlSession;

public class UpdateRoleDBOperator extends AbstractDBOperator {

    private RolePOJO role=null;

    public UpdateRoleDBOperator(RolePOJO role) {
        this.role=role;
    }

    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = MybatisConfigUtil.openSqlSession();
            RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);

            int count=roleMapper.updateRole(role);
            sqlSession.commit();
            System.err.println("UpdateRoleDBOperator execute count "+count);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            checkExCause(ex);
        }finally {
            sqlSession.close();
            return true;
        }

    }

}

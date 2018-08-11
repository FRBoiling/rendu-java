package gamedb.dao.role;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import gamedb.pojo.RolePOJO;
import org.apache.ibatis.session.SqlSession;

public class CreateRoleDBOperator extends AbstractDBOperator {

    private RolePOJO role=null;

    public CreateRoleDBOperator(RolePOJO role) {
        this.role=role;
    }

    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);

            int count=roleMapper.insertRole(role);
            sqlSession.commit();

            System.err.println("CreateRoleDBOperator execute count "+count);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }finally {
            sqlSession.close();
            return true;
        }

    }
}

package gamedb.dao.role;

import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import gamedb.pojo.role.RolePOJO;
import lombok.Getter;
import org.apache.ibatis.session.SqlSession;
@Getter
public class CreateRoleDBOperator extends AbstractDBOperator {

    private RolePOJO role=null;

    public CreateRoleDBOperator(RolePOJO role) {
        this.role=role;
    }

    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = MybatisConfigUtil.openSqlSession();
            RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);

            int count=roleMapper.insertRole(role);
            sqlSession.commit();

            System.err.println("CreateRoleDBOperator execute count "+count);
            m_result = 1;
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            checkExCause(ex);
            m_result = -1;
        }finally {
            sqlSession.close();
            return true;
        }

    }
}

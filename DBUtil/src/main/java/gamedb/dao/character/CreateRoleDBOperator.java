package gamedb.dao.character;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import gamedb.pojo.Role;
import org.apache.ibatis.session.SqlSession;

public class CreateRoleDBOperator extends AbstractDBOperator {

    private Role role=null;
    public int id;
    public int count;

    public CreateRoleDBOperator(Object arg, Role role) {
        this.role=role;
    }

    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);

            count=roleMapper.insertRole(role);
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

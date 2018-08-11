package gamedb.dao.role;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import gamedb.pojo.RolePOJO;
import org.apache.ibatis.session.SqlSession;

public class SelectRoleDBOperator extends AbstractDBOperator {

    //需要的POJOs
    private RolePOJO role = null;
    public int UId;

    public SelectRoleDBOperator(int uid) {
        this.UId = uid;
    }

    @Override
    public boolean execute() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            //HashMap<String,String> map=new HashMap<>();
            role = roleMapper.getRole(UId, "role");
            System.err.println("SelectRoleDBOperator execute");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            sqlSession.close();
            return true;
        }

    }

    public RolePOJO getRole() {
        return role;
    }

}

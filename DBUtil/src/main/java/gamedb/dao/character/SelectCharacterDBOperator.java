package gamedb.dao.character;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import gamedb.pojo.Role;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;

public class SelectCharacterDBOperator extends AbstractDBOperator {

    //需要的POJOs
    private Role role=null;
    public int id;

    public SelectCharacterDBOperator(Object arg, int id) {
        this.id=id;
        this.arg=arg;
    }

    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            RoleMapper roleMapper=sqlSession.getMapper(RoleMapper.class);
            //HashMap<String,String> map=new HashMap<>();
            role=roleMapper.getRole(1,"t_role");
            System.err.println("SelectCharacterDBOperator execute");
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }finally {
            sqlSession.close();
            return true;
        }

    }

    public Role getRole() {
        return role;
    }

}

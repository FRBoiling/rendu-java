package gamedb.dao.role;

import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import lombok.Getter;
import org.apache.ibatis.session.SqlSession;

/**
 * Created with Intellij IDEA
 * Description: 通过角色名称获取角色UID
 * User: Boil
 * Date: 2018-08-13
 * Time: 13:59
 **/

@Getter
public class GetRoleUIdByNameDBOperator extends AbstractDBOperator {
    String roleName;
    Integer UId;
    public GetRoleUIdByNameDBOperator(String roleName){
        this.roleName=roleName;
    }
    @Override
    public boolean execute() {
        SqlSession sqlSession = null;
       try{
           sqlSession = MybatisConfigUtil.openSqlSession();
           RoleMapper charMapper = sqlSession.getMapper(RoleMapper.class);
           UId = charMapper.getUIdByName("role",roleName);
           m_result = 1;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            checkExCause(ex);
            m_result = -1;
        } finally {
            sqlSession.close();
            return true;
        }
    }
}

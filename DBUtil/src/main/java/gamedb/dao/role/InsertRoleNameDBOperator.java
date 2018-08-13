package gamedb.dao.role;

import gamedb.Util.MybatisConfigUtil;
import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.RoleMapper;
import gamedb.pojo.RolePOJO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * WANART COMPANY
 * CreatedTime : 2018/8/1 10:39
 * CTEATED BY : JIANGYUNHUI
 */
@Slf4j
public class InsertRoleNameDBOperator extends AbstractDBOperator {
    String roleName;
    int UId;
    public InsertRoleNameDBOperator(String roleName, int uid){
        this.roleName=roleName;
        this.UId=uid;
    }
    @Override
    public boolean execute() {
        SqlSession sqlSession=null;
        RolePOJO pojo=new RolePOJO();
        pojo.setTableName("role");
        pojo.setRoleName(roleName);
        pojo.setUid(UId);
        try{
            sqlSession = MybatisConfigUtil.openSqlSession();
            RoleMapper charMapper=sqlSession.getMapper(RoleMapper.class);
            int count=charMapper.insertRole(pojo);
            sqlSession.commit();
            if(count>0) {
                m_result = 1;
            }else{
                m_result = 0;
            }
            return true;
        }catch (Exception ex){
            log.error(ex.getMessage());
            checkExCause(ex);
            m_result=-1;
            return false;
        }finally {
            sqlSession.close();
        }
    }
}

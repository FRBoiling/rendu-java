package gamedb.dao.accountRoleList;

import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.AccountRoleMapper;
import gamedb.pojo.account.AccountPOJO;
import gamedb.pojo.role.RolePOJO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InsertAccountRoleDBOperator extends AbstractDBOperator {

    private RolePOJO role =null;

    public InsertAccountRoleDBOperator( RolePOJO role){

        this.role = role;
    }

    @Override
    public boolean execute() {
        try{
            sqlSession = MybatisConfigUtil.openUniversalSqlSession();
            AccountRoleMapper accountRoleMapper=sqlSession.getMapper(AccountRoleMapper.class);

            int count=accountRoleMapper.insertAccountRole(role);
            sqlSession.commit();
            System.err.println("InsertAccountRoleDBOperator execute count "+count);
            if(count>0){
                m_result=1;
            }
        }catch (Exception ex){
            log.info(ex.getMessage());
            checkExCause(ex);
            m_result=-1;
            return false;
        }finally {
            sqlSession.close();
            //m_result=0;
            return true;
        }
    }
}

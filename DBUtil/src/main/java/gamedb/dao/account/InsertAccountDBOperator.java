package gamedb.dao.account;

import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.AccountMapper;
import gamedb.pojo.account.AccountPOJO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InsertAccountDBOperator extends AbstractDBOperator {
    private AccountPOJO account=null;

    public InsertAccountDBOperator(AccountPOJO account){
            this.account=account;
    }

    @Override
    public boolean execute() {
        try{
            sqlSession = MybatisConfigUtil.openUniversalSqlSession();
            AccountMapper accountMapper=sqlSession.getMapper(AccountMapper.class);

            int count=accountMapper.insertAccount(account);
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

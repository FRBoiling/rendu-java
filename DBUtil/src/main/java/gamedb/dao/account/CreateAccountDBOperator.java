package gamedb.dao.account;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.AccountMapper;
import gamedb.pojo.account.AccountPOJO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateAccountDBOperator extends AbstractDBOperator {
    private AccountPOJO account=null;

    public CreateAccountDBOperator(AccountPOJO account){
            this.account=account;
    }

    @Override
    public boolean execute() {
        try{
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            AccountMapper accountMapper=sqlSession.getMapper(AccountMapper.class);

            int count=accountMapper.insertAccount(account);
            sqlSession.commit();
            System.err.println("CreateAccountDBOperator execute count "+count);
            if(count>0){
                m_result=1;
            }
        }catch (Exception ex){
            log.info(ex.getMessage());
            m_result=-1;
            return false;
        }finally {
            sqlSession.close();
            //m_result=0;
            return true;
        }
    }
}

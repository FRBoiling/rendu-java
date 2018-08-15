package gamedb.dao.otherDao;

import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.AccountMapper;
import gamedb.interfaces.AccountRoleMapper;
import gamedb.pojo.account.AccountPOJO;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-14
 * Time: 20:02
 **/
@Slf4j
public class CreateAccountDBOperator extends AbstractDBOperator {
    private AccountPOJO account=null;

    public CreateAccountDBOperator(AccountPOJO account){
        this.account=account;
    }

    @Override
    public boolean execute() {
        try{
            sqlSession = MybatisConfigUtil.openUniversalSqlSession();
            //
            AccountMapper accountMapper=sqlSession.getMapper(AccountMapper.class);
            int count=accountMapper.insertAccount(account);
//            if(count>0) {
//                //
//                AccountRoleMapper accountRoleMapper=sqlSession.getMapper(AccountRoleMapper.class);
//                count=accountRoleMapper.insertAccountRole(account);
//            }
            sqlSession.commit();
            System.err.println("CreateAccountDBOperator execute count "+count);
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


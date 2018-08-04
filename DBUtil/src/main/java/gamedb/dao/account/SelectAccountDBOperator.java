package gamedb.dao.account;

import gamedb.Util.SqlSessionFactoryUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.AccountMapper;
import gamedb.pojo.account.AccountPOJO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SelectAccountDBOperator extends AbstractDBOperator {
    public AccountPOJO account=null;
    public int id;
    public int count;

    public SelectAccountDBOperator(AccountPOJO account){
        this.account=account;
    }

    @Override
    public boolean execute() {
        try{
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            AccountMapper accountMapper=sqlSession.getMapper(AccountMapper.class);

            account=accountMapper.getAccount(account.getAccountName(),account.getChannelName());

            sqlSession.commit();
            if(account==null) {
                m_result = 0;
            }else {
                m_result = 1;
            }
        }catch (Exception ex){
            log.info(ex.getMessage());
            m_result = -1;
            return false;
        }finally {
            sqlSession.close();
            return true;
        }
    }
}
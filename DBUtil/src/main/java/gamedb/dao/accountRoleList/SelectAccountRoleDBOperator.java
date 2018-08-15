package gamedb.dao.accountRoleList;

import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.AccountRoleMapper;
import gamedb.pojo.accountRoleList.AccountRolePOJO;
import gamedb.pojo.account.AccountPOJO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-14
 * Time: 20:17
 **/
@Slf4j
public class SelectAccountRoleDBOperator extends AbstractDBOperator {
    @Getter
    private Map<Integer, AccountRolePOJO> accountRolePOJOs = null;
    private AccountPOJO account = null;

    public SelectAccountRoleDBOperator(AccountPOJO account) {
        this.account = account;
    }

    @Override
    public boolean execute() {
        try {
            sqlSession = MybatisConfigUtil.openUniversalSqlSession();
            AccountRoleMapper accountRoleMapper = sqlSession.getMapper(AccountRoleMapper.class);
            accountRolePOJOs = accountRoleMapper.selectAccountRoles(account.getUsername());
            sqlSession.commit();
            if (account == null) {
                m_result = 0;
            } else {
                m_result = 1;
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            checkExCause(ex);
            m_result = -1;
            return false;
        } finally {
            sqlSession.close();
            return true;
        }
    }
}
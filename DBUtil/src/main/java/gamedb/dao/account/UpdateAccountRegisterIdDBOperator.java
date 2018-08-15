package gamedb.dao.account;

import gamedb.Util.MybatisConfigUtil;
import gamedb.dao.AbstractDBOperator;
import gamedb.interfaces.AccountMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-14
 * Time: 14:47
 **/
public class UpdateAccountRegisterIdDBOperator extends AbstractDBOperator {
    String username ;
    String channelName ;
    String registerId ;

    public UpdateAccountRegisterIdDBOperator(String username, String channelName, String registerId){
        this.username = username;
        this.channelName = channelName;
        this.registerId = registerId;
    }

    @Override
    public boolean execute() {
        SqlSession sqlSession=null;
        try{
            sqlSession = MybatisConfigUtil.openUniversalSqlSession();
            AccountMapper accountMapper=sqlSession.getMapper(AccountMapper.class);
//            int count=accountMapper.updateRegisterId(username,channelName,registerId);
            sqlSession.commit();
//            System.err.println("UpdateAccountRegisterIdDBOperator execute count "+count);
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            checkExCause(ex);
        }finally {
            sqlSession.close();
            return true;
        }
    }
}

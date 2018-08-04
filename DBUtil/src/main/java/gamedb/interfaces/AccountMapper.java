package gamedb.interfaces;

import gamedb.pojo.account.AccountPOJO;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    public int insertAccount(AccountPOJO accountPOJO);
    public AccountPOJO getAccount(@Param("accountName") String accountName,@Param("channelName") String channelName);
}

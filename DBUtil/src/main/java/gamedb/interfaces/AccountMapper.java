package gamedb.interfaces;

import gamedb.pojo.account.AccountPOJO;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    int insertAccount(AccountPOJO accountPOJO);
    AccountPOJO getAccount(@Param("username") String username);
    int updateRegisterId(@Param("username") String username,@Param("registerId") String registerId);
}

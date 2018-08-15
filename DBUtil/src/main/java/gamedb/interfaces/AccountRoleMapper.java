package gamedb.interfaces;

import gamedb.pojo.accountRoleList.AccountRolePOJO;
import gamedb.pojo.account.AccountPOJO;
import gamedb.pojo.role.RolePOJO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-14
 * Time: 18:47
 **/
public interface AccountRoleMapper {
    int insertAccountRole(RolePOJO rolePOJO);

    @MapKey("role_uid")
    Map<Integer,AccountRolePOJO> selectAccountRoles(@Param("username") String userName);

    int updateRoleName(@Param("roleUid") Integer roleUid,@Param("roleName") String roleName);
}

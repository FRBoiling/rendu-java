package gamedb.pojo.accountRoleList;

import lombok.Getter;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-08-14
 * Time: 18:48
 **/
@Getter
public class AccountRolePOJO {
    String tableName="account_role_list";

    int uid=0;
    int role_uid=0;
    String username="";
    int areaId=0;
    String roleName;
    String channelName;
}

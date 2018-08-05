package dataObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-07-18
 * Time: 11:11
 **/
@Getter
@Setter
public class AccountObject {

    public AccountObject(String  accountName){
    this.accountName =accountName;
    }

    String accountName="";
    String deviceId="";
    String registerId="";
    String channelName="default";

    boolean isOffline =true;
}

package gamedb.pojo.account;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
public class AccountPOJO {
    String accountName;
    String channelName;
    String deviceId;
    String password;
    String registerId;
    String charUids;
    Timestamp timeCreated;


    String tableName;
}

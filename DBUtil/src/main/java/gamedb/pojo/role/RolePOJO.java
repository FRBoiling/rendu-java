package gamedb.pojo.role;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RolePOJO {
    String tableName="role";

    int uid;
    String roleName;

    Timestamp createTime;

    String username;
    int areaId;
    int faceIconId;

    String channelName;
}

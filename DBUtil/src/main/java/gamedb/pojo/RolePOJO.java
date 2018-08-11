package gamedb.pojo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RolePOJO {
    String tableName;

    int uid;
    String roleName;
    int groupId;

    String username;
    String channelName;

    Timestamp createTime;

    int level;
    int ladderLevel;

    int faceIconId;
    int fashionHead =0;
    int sex;

    int fashionWeapon = 0;
    int fashionFace = 0;
    int fashionClothes = 0;
    int fashionBack = 0;
}

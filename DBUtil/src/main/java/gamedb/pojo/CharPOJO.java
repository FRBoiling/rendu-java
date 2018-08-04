package gamedb.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharPOJO {
    String tableName;

    int uid;
    String accountName;
    String channelName;
    String charName;
    int main;
    int channel = 1;

    int mapId;
    float posX;
    float posY;

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

package gamedb.interfaces;

import gamedb.pojo.CharPOJO;
import org.apache.ibatis.annotations.Param;

public interface CharMapper {
    int getMaxUid(@Param("tableName") String tableName);
    int insertCharacter(CharPOJO role);
    int insertCharacterName(CharPOJO nameRole);
}

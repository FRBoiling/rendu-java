package gamedb.interfaces;

import gamedb.pojo.RolePOJO;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int getMaxUid(@Param("tableName") String tableName);
    RolePOJO getRole(@Param("uid") int uid,@Param("tableName") String tableName);
    int insertRole(RolePOJO role);
    int updateRole(RolePOJO role);
    int deleteRole(RolePOJO role);
}

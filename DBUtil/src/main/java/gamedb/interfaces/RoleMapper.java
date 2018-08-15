package gamedb.interfaces;

import gamedb.pojo.role.RolePOJO;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    Integer getMaxUid(@Param("tableName") String tableName);
    Integer getUIdByName(@Param("tableName") String tableName,@Param("roleName") String roleName);
    RolePOJO getRole(@Param("uid") int uid,@Param("tableName") String tableName);
    int insertRole(RolePOJO role);
    int updateRole(RolePOJO role);
    int deleteRole(RolePOJO role);
}

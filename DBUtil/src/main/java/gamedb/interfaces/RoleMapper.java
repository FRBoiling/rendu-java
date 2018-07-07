package gamedb.interfaces;

import gamedb.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

public interface RoleMapper {
    public Role getRole(@Param("id") int id,@Param("tableName") String tableName);
    public int insertRole(Role role);
    public int updateRole(Role role);
    public int deleteRole(Role role);
}

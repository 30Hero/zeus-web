package vn.zeus.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.zeus.web.domain.dto.response.UserPermissionResponse;
import vn.zeus.web.domain.model.RolePermission;

@Mapper
public interface RolePermissionMapper {
	public RolePermission selectById(Long id);

	public RolePermission selectOne(RolePermission rolePermission);

	public List<RolePermission> selectList(RolePermission rolePermission);

	public int insertOne(RolePermission rolePermission);

	public int update(RolePermission rolePermission);

	public int deleteOne(Long id);

	public int deleteList(@Param("ids") Long[] ids);

	public List<UserPermissionResponse> selectListPerm(Long roleId);
}
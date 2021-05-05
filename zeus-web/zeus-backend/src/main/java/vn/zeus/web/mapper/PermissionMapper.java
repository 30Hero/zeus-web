package vn.zeus.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.zeus.web.domain.model.Permission;

@Mapper
public interface PermissionMapper {
	public Permission selectById(Long id);

	public Permission selectOne(Permission permission);

	public List<Permission> selectList(Permission permission);

	public int insertOne(Permission permission);

	public int update(Permission permission);

	public int deleteOne(Long id);

	public int deleteList(@Param("ids") Long[] ids);

}

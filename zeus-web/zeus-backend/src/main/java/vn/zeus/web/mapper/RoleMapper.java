package vn.zeus.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.zeus.web.domain.model.Role;

@Mapper
public interface RoleMapper {
	public Role selectById(Long id);

	public Role selectOne(Role role);

	public List<Role> selectList(Role role);

	public int insertOne(Role role);

	public int update(Role role);

	public int deleteOne(Long id);

	public int deleteList(@Param("ids") Long[] ids);

}
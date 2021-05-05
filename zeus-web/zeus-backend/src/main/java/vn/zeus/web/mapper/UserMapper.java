package vn.zeus.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import vn.zeus.web.domain.dto.request.F002SearchUserRequest;
import vn.zeus.web.domain.dto.response.F002SearchUserResponse;
import vn.zeus.web.domain.model.User;

@Mapper
public interface UserMapper {
	public User selectById(Long id);

	public User selectOne(User user);

	public List<User> selectList(User user);

	public int insertOne(User user);

	public int update(User user);

	public int deleteOne(Long id);

	public int deleteList(@Param("ids") Long[] ids);
	
	public Page<F002SearchUserResponse> f002SearchUser(F002SearchUserRequest request);

}

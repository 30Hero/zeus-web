package vn.zeus.web.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.zeus.web.domain.dto.response.UserInfoResponse;
import vn.zeus.web.domain.model.User;
import vn.zeus.web.exception.BusinessException;
import vn.zeus.web.mapper.RolePermissionMapper;
import vn.zeus.web.mapper.UserMapper;
import vn.zeus.web.message.MessageHelper;
import vn.zeus.web.message.Message.Keys;
import vn.zeus.web.util.ModelMapperUtils;

@Service
public class UserService extends BaseService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	public UserInfoResponse getUserInfo(User user) {
		UserInfoResponse userInfoResponse = new UserInfoResponse();
		userInfoResponse = ModelMapperUtils.map(user, UserInfoResponse.class);

		userInfoResponse.setPerms(rolePermissionMapper.selectListPerm(user.getRoleId()));
		return userInfoResponse;
	}

	public User findUserByUsername(String username) {
		User userSelect = new User();
		userSelect.setUserName(username);
		userSelect.setDelFlag(false);

		return userMapper.selectOne(userSelect);
	}

	public User findUserById(Long id) {
		User userSelect = new User();
		userSelect.setId(id);
		userSelect.setDelFlag(false);

		return userMapper.selectOne(userSelect);
	}
}

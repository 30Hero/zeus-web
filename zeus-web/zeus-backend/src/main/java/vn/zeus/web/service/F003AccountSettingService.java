package vn.zeus.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.zeus.web.domain.dto.request.F003UpdateUserBasicInfoRequest;
import vn.zeus.web.domain.model.User;
import vn.zeus.web.mapper.UserMapper;
import vn.zeus.web.util.StringUtils;

@Service
public class F003AccountSettingService extends BaseService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Cập nhật thông tin cá nhân
	 */
	@Transactional
	public void updateBasicInfo(F003UpdateUserBasicInfoRequest request, User currentUser) {
		String fullName = StringUtils.isEmpty(request.getFullName()) ? null : request.getFullName().trim();
		String email = StringUtils.isEmpty(request.getEmail()) ? null : request.getEmail().trim();
		String phone = StringUtils.isEmpty(request.getPhone()) ? null : request.getPhone().trim();

		User userUpdate = new User();
		userUpdate.setFullName(fullName);
		userUpdate.setEmail(email);
		userUpdate.setPhone(phone);
		userUpdate.setId(currentUser.getId());
		userMapper.update(userUpdate);
	}
}

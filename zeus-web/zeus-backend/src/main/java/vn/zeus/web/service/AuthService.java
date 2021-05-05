package vn.zeus.web.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import vn.zeus.web.domain.dto.request.LoginRequest;
import vn.zeus.web.domain.dto.response.LoginResponse;
import vn.zeus.web.domain.model.User;
import vn.zeus.web.exception.AuthenticationException;
import vn.zeus.web.exception.BusinessException;
import vn.zeus.web.mapper.UserMapper;
import vn.zeus.web.message.MessageHelper;
import vn.zeus.web.message.Message.Keys;
import vn.zeus.web.security.AuthToken;
import vn.zeus.web.security.TokenProvider;

@Service
@Slf4j
public class AuthService extends BaseService{
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Login by username
	 * @param loginRequest
	 * @return
	 */
	@Transactional
	public LoginResponse login(LoginRequest request) {
		Authentication authentication = null;
		String username = request.getUsername();
		String password = request.getPassword();
		
		User userSelect = new User();
		userSelect.setUserName(username);
		userSelect.setDelFlag(false);
		
		User user = userMapper.selectOne(userSelect);

		if (Objects.isNull(user)) {
			throw new BusinessException(MessageHelper.getMessage(Keys.E0007), new Throwable());
		}

		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					username, password));
		} catch (Exception ex) {
			throw new BusinessException(MessageHelper.getMessage(Keys.E0007), new Throwable());
		}

		if (Objects.isNull(authentication)) {
			return null;
		}

		// Create token
		AuthToken authToken = tokenProvider.createToken(authentication);

		// Add authentication info to security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAuthToken(authToken.getToken());

		loginResponse.setMe(userService.getUserInfo(user));

		return loginResponse;
	}
}

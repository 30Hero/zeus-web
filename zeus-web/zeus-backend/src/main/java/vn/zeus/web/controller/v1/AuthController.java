package vn.zeus.web.controller.v1;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vn.zeus.web.annotation.NoAuth;
import vn.zeus.web.controller.BaseController;
import vn.zeus.web.domain.dto.request.LoginRequest;
import vn.zeus.web.domain.dto.response.LoginResponse;
import vn.zeus.web.domain.dto.response.UserInfoResponse;
import vn.zeus.web.framework.http.ApiResult;
import vn.zeus.web.framework.http.ResponseData;
import vn.zeus.web.security.CurrentUser;
import vn.zeus.web.security.UserPrincipal;
import vn.zeus.web.service.AuthService;
import vn.zeus.web.service.UserService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthController extends BaseController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/login")
	@ResponseBody
	@NoAuth
	public ResponseEntity<ResponseData> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
			HttpServletRequest request) {
		ResponseData responseData = new ResponseData();

		loginRequest.setUserAgent(getUserAgent(request));
		loginRequest.setRemoteAddr(getRemoteAddr(request));

		responseData.setResult(authService.login(loginRequest));
		return ApiResult.success(responseData);
	}

	@GetMapping("/me")
	public ResponseEntity<ResponseData> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		ResponseData responseData = new ResponseData();
		responseData.setResult(userService.getUserInfo(userPrincipal.getCurrentUser()));
		return ApiResult.success(responseData);
	}

	@PostMapping("/logout")
	public ResponseEntity<ResponseData> logout(@CurrentUser UserPrincipal userPrincipal, HttpServletRequest request)
			throws Exception {
		request.logout();
		return ApiResult.success();
	}
}

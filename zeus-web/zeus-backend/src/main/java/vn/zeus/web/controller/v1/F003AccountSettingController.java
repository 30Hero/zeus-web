package vn.zeus.web.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.zeus.web.controller.BaseController;
import vn.zeus.web.domain.dto.request.F003UpdateUserBasicInfoRequest;
import vn.zeus.web.framework.http.ApiResult;
import vn.zeus.web.framework.http.ResponseData;
import vn.zeus.web.security.CurrentUser;
import vn.zeus.web.security.UserPrincipal;
import vn.zeus.web.service.F003AccountSettingService;

@RestController
@RequestMapping("/api/v1/f003")
@CrossOrigin
public class F003AccountSettingController extends BaseController {

	@Autowired
	private F003AccountSettingService f003AccountSettingService;

	@PutMapping("/updateBasicInfo")
	public ResponseEntity<ResponseData> updateBasicInfo(@Valid @RequestBody F003UpdateUserBasicInfoRequest request,
			@CurrentUser UserPrincipal userPrincipal) {
		f003AccountSettingService.updateBasicInfo(request, userPrincipal.getCurrentUser());
		return ApiResult.success();
	}

}

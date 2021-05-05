package vn.zeus.web.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vn.zeus.web.controller.BaseController;
import vn.zeus.web.domain.dto.request.F002CreateNewAccountRequest;
import vn.zeus.web.domain.dto.request.F002SearchUserRequest;
import vn.zeus.web.domain.dto.request.F002UpdateAccountRequest;
import vn.zeus.web.framework.http.ApiResult;
import vn.zeus.web.framework.http.ResponseData;
import vn.zeus.web.service.F002AccountManagerService;

@RestController
@RequestMapping("/api/v1/f002")
@CrossOrigin
public class F002AccountManagerController extends BaseController {

	@Autowired
	private F002AccountManagerService f002AccountManagerService;

	@GetMapping("/initScreen")
	@ResponseBody
	public ResponseEntity<ResponseData> initScreen() {
		return ApiResult.success(f002AccountManagerService.getDataInitScreen());
	}

	@PostMapping("/search")
	@ResponseBody
	public ResponseEntity<ResponseData> searchUser(@RequestBody F002SearchUserRequest request) {
		return ApiResult.success(f002AccountManagerService.searchUser(request));
	}

	@PostMapping("/downloadExcel")
	public ResponseEntity<ResponseData> downloadExcel(@RequestBody F002SearchUserRequest request) {
		return ApiResult.success(f002AccountManagerService.downloadExcel(request));
	}

	@PostMapping("/createNewAccount")
	public ResponseEntity<ResponseData> createNewAccount(@Valid @RequestBody F002CreateNewAccountRequest request) {
		f002AccountManagerService.createNewAccount(request);
		return ApiResult.success();
	}
	
	@PutMapping("/updateAccount")
	public ResponseEntity<ResponseData> updateAccount(@Valid @RequestBody F002UpdateAccountRequest request) {
		f002AccountManagerService.updateAccount(request);
		return ApiResult.success();
	}

}

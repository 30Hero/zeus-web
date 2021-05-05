package vn.zeus.web.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import vn.zeus.web.domain.validators.constraints.ExistRoleId;
import vn.zeus.web.domain.validators.constraints.NotExistEmail;
import vn.zeus.web.domain.validators.constraints.NotExistPhoneNumber;
import vn.zeus.web.domain.validators.constraints.PhoneNumber;
import vn.zeus.web.domain.validators.constraints.UserPassword;

@Getter
@Setter
public class F002UpdateAccountRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;

	@Size(min = 1, max = 150)
	private String fullName;

	@Email
	@Size(min = 1, max = 255)
	@NotExistEmail
	private String email;

	@Size(min = 1, max = 20)
	@PhoneNumber
	@NotExistPhoneNumber
	private String phone;

	@Size(min = 3, max = 150)
	@UserPassword
	private String password;

	@Size(min = 1, max = 150)
	private String repeatPassword;

	@ExistRoleId
	@NotNull
	private Long roleId;

	private Boolean delFlag = false;

}

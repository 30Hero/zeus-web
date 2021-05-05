package vn.zeus.web.domain.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;

}

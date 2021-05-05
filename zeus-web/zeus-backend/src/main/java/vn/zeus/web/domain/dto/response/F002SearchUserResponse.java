package vn.zeus.web.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class F002SearchUserResponse extends BaseResponse {

	private Long id;

	private String userName;

	private Long roleId;

	private String fullName;

	private String phone;

	private String email;

	private Boolean delFlag;

	private String roleName;
}

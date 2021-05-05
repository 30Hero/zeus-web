package vn.zeus.web.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class F002SearchUserRequest extends PageAbleRequest {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String userName;

	private String fullName;

	private Long[] roleIds;
}

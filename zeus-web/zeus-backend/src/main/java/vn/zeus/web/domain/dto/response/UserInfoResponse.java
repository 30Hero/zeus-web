package vn.zeus.web.domain.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.zeus.web.domain.model.User;

@Getter
@Setter
public class UserInfoResponse extends User {
	private List<UserPermissionResponse> perms;
}

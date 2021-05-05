package vn.zeus.web.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPermissionResponse extends BaseResponse {

	private String screenId;

	private String screenName;

	private String functionId;

	private String functionName;

	private String actionId;

	private String actionName;

	private Long permissionId;

	private Integer displayOperationControl;

}

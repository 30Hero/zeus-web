package vn.zeus.web.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermission extends BaseModel{
	private Long roleId;

	private Long permissionId;

	private Integer displayOperationControl;

}
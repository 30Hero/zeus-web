package vn.zeus.web.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission extends BaseModel{
	private Long id;

	private String screenId;

	private String screenName;

	private String functionId;

	private String functionName;

	private String actionId;

	private String actionName;

}
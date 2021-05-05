package vn.zeus.web.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends BaseModel{
	private Long id;

	private String name;

	private String description;

	private Boolean delFlag;

}
package vn.zeus.web.domain.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseModel{
	private Long id;

	private String userName;

	@JsonIgnore
	private String password;

	private Long roleId;

	private String fullName;

	private String phone;

	private String email;

	private String address;

	private String img;

	private String remark;

	private String loginIp;

	private Date loginDate;

	private Boolean delFlag;

}

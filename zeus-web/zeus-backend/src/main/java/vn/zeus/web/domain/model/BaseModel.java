package vn.zeus.web.domain.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseModel {
	@JsonIgnore
	private Integer version;

	@JsonIgnore
	private Date createdAt;

	@JsonIgnore
	private Date updatedAt;

	@JsonIgnore
	private Integer updatedBy;
}

package vn.zeus.web.framework.http;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResultCode {
	SUCCESS(200), ERROR(400), TIMEOUT(401);

	private Integer value;

	private ResultCode(int value) {
		this.value = value;
	}
	
	@JsonValue
	public Integer getValue() {
		return value;
	}
}

package vn.zeus.web.framework.http;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
	private String content;
	
	private Map<String, String> fieldErrs;
}

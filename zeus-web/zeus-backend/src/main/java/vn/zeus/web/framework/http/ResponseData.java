package vn.zeus.web.framework.http;

import java.util.Objects;
import java.util.TreeMap;

public class ResponseData extends TreeMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ResponseData() {
		super(new TreeMap<>());
		put("code", null);
		put("message", null);
		put("result", null);
	}

	public void setCode(ResultCode code) {
		put("code", code);
	}

	public void setMessage(ErrorMessage message) {
		put("message", message);
	}

	public void setResult(Object result) {
		put("result", result);
	}

	@SuppressWarnings("unchecked")
	public void addResult(String key, Object data) {
		TreeMap<String, Object> result = null;
		try {
			result = (TreeMap<String, Object>) get("result");
		} catch (Exception e) {
		}
		if (Objects.isNull(result)) {
			result = new TreeMap<String, Object>();
		}
		result.put(key, data);
		put("result", result);
	}
}

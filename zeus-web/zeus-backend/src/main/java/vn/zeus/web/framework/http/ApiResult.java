package vn.zeus.web.framework.http;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;
import vn.zeus.web.message.Message;
import vn.zeus.web.message.MessageHelper;
import vn.zeus.web.util.MapUtils;

@Getter
@Setter
public class ApiResult {

	public static ResponseEntity<ResponseData> success() {
		return response(HttpStatus.OK, MessageHelper.getMessage(Message.Keys.I0001), new ResponseData(), null);
	}

	public static ResponseEntity<ResponseData> success(ResponseData data) {
		return response(HttpStatus.OK, MessageHelper.getMessage(Message.Keys.I0001), data, null);
	}

	public static ResponseEntity<ResponseData> failed() {
		return response(HttpStatus.BAD_REQUEST, MessageHelper.getMessage(Message.Keys.I0002), new ResponseData(), null);
	}

	public static ResponseEntity<ResponseData> failed(Message message) {
		return response(HttpStatus.BAD_REQUEST, message, new ResponseData(), null);
	}

	public static ResponseEntity<ResponseData> response(HttpStatus httpStatus, Message message, ResponseData data,
			Map<String, String> fieldErrs) {
		if (data == null) {
			data = new ResponseData();
		}
		if (httpStatus.equals(HttpStatus.OK)) {
			data.setCode(ResultCode.SUCCESS);
		} else {
			if (httpStatus.equals(HttpStatus.UNAUTHORIZED)) {
				data.setCode(ResultCode.TIMEOUT);
			} else {
				data.setCode(ResultCode.ERROR);
			}
			ErrorMessage errorMessage = new ErrorMessage();
			if (Objects.nonNull(message)) {
				errorMessage.setContent(message.getContent());
			}
			if (MapUtils.isNotEmpty(fieldErrs)) {
				errorMessage.setFieldErrs(fieldErrs);
			}
			data.setMessage(errorMessage);
			data.setResult(null);
		}
		return ResponseEntity.status(httpStatus).body(data);
	}

}

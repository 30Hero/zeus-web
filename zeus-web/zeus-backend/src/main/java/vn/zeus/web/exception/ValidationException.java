package vn.zeus.web.exception;

import javax.print.attribute.standard.Severity;

import lombok.Getter;
import lombok.Setter;
import vn.zeus.web.message.Message;

@Getter
@Setter
public class ValidationException extends BaseException {
	
	private String field;

	private static final long serialVersionUID = 1L;

	public ValidationException(String field, Message message, Throwable rootCause) {
		super(message, Severity.WARNING, rootCause);
		this.field = field;
	}

}

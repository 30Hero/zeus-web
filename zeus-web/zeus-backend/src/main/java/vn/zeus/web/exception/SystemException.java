package vn.zeus.web.exception;

import javax.print.attribute.standard.Severity;

import vn.zeus.web.message.Message;

public class SystemException extends BaseException {

	private static final long serialVersionUID = 1L;

	public SystemException(Message message, Throwable rootCause) {
		super(message, Severity.ERROR, rootCause);
	}
}

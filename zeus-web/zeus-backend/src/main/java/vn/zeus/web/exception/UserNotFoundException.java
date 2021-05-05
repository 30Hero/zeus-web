package vn.zeus.web.exception;

import vn.zeus.web.message.Message;
import vn.zeus.web.message.MessageHelper;

public class UserNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String fieldName, Object fieldValue, Throwable rootCause) {
		super(MessageHelper.getMessage(Message.Keys.E0003, fieldName, fieldValue), rootCause);
	}
}

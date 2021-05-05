package vn.zeus.web.exception;

import javax.print.attribute.standard.Severity;

import vn.zeus.web.message.Message;

public class BusinessException extends BaseException {
  private static final long serialVersionUID = 1L;

  public BusinessException(Message message, Throwable rootCause) {
    super(message, Severity.WARNING, rootCause);
  }
}

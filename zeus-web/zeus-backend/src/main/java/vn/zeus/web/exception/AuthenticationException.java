package vn.zeus.web.exception;

import javax.print.attribute.standard.Severity;

import vn.zeus.web.message.Message;

public class AuthenticationException extends BaseException {
  private static final long serialVersionUID = 1L;

  public AuthenticationException(Message message, Throwable rootCause) {
    super(message, Severity.WARNING, rootCause);
  }
}

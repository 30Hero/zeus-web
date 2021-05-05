package vn.zeus.web.exception;

import vn.zeus.web.message.Message;

public class OAuth2AuthenticationException extends AuthenticationException {
  private static final long serialVersionUID = 1L;

  public OAuth2AuthenticationException(Message message, Throwable rootCause) {
    super(message, rootCause);
  }
}

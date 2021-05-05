package vn.zeus.web.exception;

import javax.print.attribute.standard.Severity;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import vn.zeus.web.message.Message;

@Getter
@Setter
public class BaseException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private Message msg;
  private Severity severity;
  @JsonIgnore
  private Throwable rootCause;

  public BaseException(Message message, Severity severity, Throwable rootCause) {
    super(message.getContent(), rootCause);
    this.msg = message;
    this.severity = severity;
    this.rootCause = rootCause;
  }

  public BaseException(String string, String code, Object[] args, Object object) {
    
  }
}

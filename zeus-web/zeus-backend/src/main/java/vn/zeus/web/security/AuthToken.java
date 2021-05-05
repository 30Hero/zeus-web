package vn.zeus.web.security;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.zeus.web.domain.types.TokenType;

@Getter
@Setter
public class AuthToken {
  private String token;
  private TokenType tokenType;
  private Date created;
  private Date expiration;
}

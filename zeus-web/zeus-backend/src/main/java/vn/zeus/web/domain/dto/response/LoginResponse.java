package vn.zeus.web.domain.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.zeus.web.domain.types.TokenType;

@Getter
@Setter
public class LoginResponse extends BaseResponse {

	private String authToken;
	private TokenType tokenType = TokenType.Bearer;
	private UserInfoResponse me;
}
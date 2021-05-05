package vn.zeus.web.domain.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userAgent;

	private String remoteAddr;

}

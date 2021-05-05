package vn.zeus.web.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageAbleRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	private Integer pageNum;

	private Integer pageSize;

	private String sorts;
}

package vn.zeus.web.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	/**
	 * Get user agent
	 *
	 * @param request
	 * @return
	 */
	public String getUserAgent(HttpServletRequest request) {
		if (Objects.isNull(request)) {
			return null;
		}

		return request.getHeader("User-Agent");
	}

	/**
	 * Get remote address
	 *
	 * @param request
	 * @return
	 */
	public String getRemoteAddr(HttpServletRequest request) {
		if (Objects.isNull(request)) {
			return null;
		}

		return request.getRemoteAddr();
	}

}

package vn.zeus.web.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.zeus.web.annotation.NoAuth;
import vn.zeus.web.domain.types.TokenType;
import vn.zeus.web.framework.http.ApiResult;
import vn.zeus.web.framework.http.ResponseData;
import vn.zeus.web.message.Message;
import vn.zeus.web.message.MessageHelper;

public class JwtAuthenticationInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	private String[] WHITE_LIST = new String[] {};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String requestURI = request.getRequestURI();
		for (String uri : WHITE_LIST) {
			if (requestURI.contains(uri) && StringUtils.isEmpty(getJwtFromRequest(request))) {
				return true;
			}
		}

		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Method method = hm.getMethod();
			if (method.isAnnotationPresent(NoAuth.class)) {
				return true;
			}
		}

		if (handler instanceof ResourceHttpRequestHandler) {
			return true;
		}

		if (HttpStatus.NOT_FOUND.value() == response.getStatus()) {
			setResponseError(response, HttpStatus.NOT_FOUND);
			return false;
		}

		try {
			String jwt = getJwtFromRequest(request);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Integer userId = tokenProvider.getUserIdFromToken(jwt);
				UserDetails userDetails = customUserDetailsService.loadUserById(userId);

				if (!verifyUser(userDetails)) {
					setResponseError(response, HttpStatus.UNAUTHORIZED);
					return false;
				}
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				// Create refresh token
				AuthToken refreshToken = tokenProvider.createToken(authentication);
				response.setHeader("RefreshToken", refreshToken.getToken());

				return true;
			} else {
				setResponseError(response, HttpStatus.UNAUTHORIZED);
				return false;
			}
		} catch (Exception ex) {
			setResponseError(response, HttpStatus.UNAUTHORIZED);
			return false;
		}
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TokenType.Bearer.name())) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private Boolean verifyUser(UserDetails userDetails) {
		if (Objects.isNull(userDetails)) {
			return false;
		}

		if (!userDetails.isEnabled()) {
			return false;
		}

		if (!userDetails.isAccountNonExpired()) {
			return false;
		}

		if (!userDetails.isAccountNonLocked()) {
			return false;
		}

		return userDetails.isCredentialsNonExpired();
	}

	private void setResponseError(HttpServletResponse response, HttpStatus httpStatus) throws IOException {
		ResponseEntity<ResponseData> result = ApiResult.failed();
		switch (httpStatus) {
		case UNAUTHORIZED:
			result = ApiResult.response(httpStatus, MessageHelper.getMessage(Message.Keys.E0002), null, null);
			break;
		case NOT_FOUND:
			result = ApiResult.response(httpStatus, MessageHelper.getMessage(Message.Keys.E0006), null, null);
		default:
			break;
		}
		byte[] responseToSend = new ObjectMapper().writeValueAsString(result.getBody()).getBytes();
		response.setHeader("Content-type", "application/json");
		response.setStatus(httpStatus.value());
		response.getOutputStream().write(responseToSend);
	}
}

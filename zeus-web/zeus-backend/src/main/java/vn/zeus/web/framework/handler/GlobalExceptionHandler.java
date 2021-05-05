package vn.zeus.web.framework.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import vn.zeus.web.exception.AuthenticationException;
import vn.zeus.web.exception.BusinessException;
import vn.zeus.web.exception.ResourceNotFoundException;
import vn.zeus.web.exception.SystemException;
import vn.zeus.web.exception.ValidationException;
import vn.zeus.web.framework.http.ApiResult;
import vn.zeus.web.framework.http.ResponseData;
import vn.zeus.web.message.Message;
import vn.zeus.web.message.MessageHelper;
import vn.zeus.web.util.MapUtils;

/**
 * Created by 30Hero on 12/17/2020
 **/

@RestControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ResponseData> handleAuthenticationException(AuthenticationException exception) {
		log.warn(exception.getMessage(), exception);
		return ApiResult.response(HttpStatus.UNAUTHORIZED, exception.getMsg(), null, null);
	}

	@ResponseBody
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseData> handleResourceNotFoundException(ResourceNotFoundException exception) {
		log.error(exception.getMessage(), exception);
		return ApiResult.response(HttpStatus.NOT_FOUND, exception.getMsg(), null, null);
	}

	@ResponseBody
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ResponseData> handleBusinessException(BusinessException exception) {
		log.warn(exception.getMessage(), exception);
		return ApiResult.response(HttpStatus.BAD_REQUEST, exception.getMsg(), null, null);
	}

	@ResponseBody
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ResponseData> handleValidationException(ValidationException exception) {
		log.warn(exception.getMessage(), exception);

		Message message = new Message();
		Map<String, String> fieldErrs = new HashMap<>();
		fieldErrs.put(exception.getField(), exception.getMsg().getContent());

		message.setContent(exception.getMsg().getContent());
		return ApiResult.response(HttpStatus.UNPROCESSABLE_ENTITY, message, null, fieldErrs);
	}

	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseData> handleRuntimeException(RuntimeException exception) {
		log.error(exception.getMessage(), exception);
		return ApiResult.response(HttpStatus.INTERNAL_SERVER_ERROR, MessageHelper.getMessage(Message.Keys.E0001), null,
				null);
	}

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseData> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		log.warn(exception.getMessage(), exception);

		Message message = new Message();
		Map<String, String> fieldErrs = new LinkedHashMap<>();
		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			if ("NotBlank".equals(error.getCode()) || "NotNull".equals(error.getCode())
					|| "NotEmpty".equals(error.getCode())) {
				fieldErrs.put(error.getField(), error.getDefaultMessage());
			} else if (!fieldErrs.containsKey(error.getField())) {
				fieldErrs.put(error.getField(), error.getDefaultMessage());
			}
		}

		if (MapUtils.isNotEmpty(fieldErrs)) {
			Map.Entry<String, String> entry = fieldErrs.entrySet().iterator().next();
			message.setContent(entry.getValue());
		}
		return ApiResult.response(HttpStatus.UNPROCESSABLE_ENTITY, message, null, fieldErrs);
	}

	@ResponseBody
	@ExceptionHandler(SystemException.class)
	public ResponseEntity<ResponseData> handleSystemException(SystemException exception) {
		log.error(exception.getMessage(), exception);
		return ApiResult.response(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMsg(), null, null);
	}

	@ResponseBody
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ResponseData> handleIOException(IOException exception) {
		log.error(exception.getMessage(), exception);
		return ApiResult.response(HttpStatus.INTERNAL_SERVER_ERROR, MessageHelper.getMessage(Message.Keys.E0001), null,
				null);
	}

	@ResponseBody
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ResponseData> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException exception) {
		log.warn(exception.getMessage(), exception);
		Message message = new Message();
		message.setId(Message.Keys.E0010);
		message.setContent(exception.getMessage());
		return ApiResult.response(HttpStatus.METHOD_NOT_ALLOWED, message, null, null);
	}

	@ResponseBody
	@ExceptionHandler(ServletRequestBindingException.class)
	public ResponseEntity<ResponseData> handleServletRequestBindingException(ServletRequestBindingException exception) {
		log.warn(exception.getMessage(), exception);
		Message message = new Message();
		message.setId(Message.Keys.E0011);
		message.setContent(exception.getMessage());
		return ApiResult.response(HttpStatus.BAD_REQUEST, message, null, null);
	}

	@ResponseBody
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResponseData> handleAccessDeniedException(AccessDeniedException exception) {
		log.warn(exception.getMessage(), exception);
		return ApiResult.response(HttpStatus.UNAUTHORIZED, MessageHelper.getMessage(Message.Keys.E0009), null, null);
	}

	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseData> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		log.warn(exception.getMessage(), exception);
		Message message = new Message();
		message.setContent(exception.getMessage());
		return ApiResult.response(HttpStatus.BAD_REQUEST, message, null, null);
	}

	@ResponseBody
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ResponseData> handleHttpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException exception) {
		log.warn(exception.getMessage(), exception);
		Message message = new Message();
		message.setContent(exception.getMessage());
		return ApiResult.response(HttpStatus.BAD_REQUEST, message, null, null);
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseData> handleException(Exception exception) {
		log.error(exception.getMessage(), exception);
		return ApiResult.response(HttpStatus.INTERNAL_SERVER_ERROR, MessageHelper.getMessage(Message.Keys.E0001), null,
				null);
	}
}

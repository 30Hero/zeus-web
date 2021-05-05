package vn.zeus.web.domain.validators;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import vn.zeus.web.domain.model.User;
import vn.zeus.web.domain.validators.constraints.NotExistEmail;
import vn.zeus.web.mapper.UserMapper;

public class NotExistEmailValidator implements ConstraintValidator<NotExistEmail, String> {

	@Autowired
	private UserMapper userMapper;

	@Override
	public void initialize(NotExistEmail constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (Objects.isNull(value)) {
			return true;
		}

		User userSelect = new User();
		userSelect.setEmail(value.trim());

		return userMapper.selectOne(userSelect) == null;
	}
}

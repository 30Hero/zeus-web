package vn.zeus.web.domain.validators;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import vn.zeus.web.domain.model.User;
import vn.zeus.web.domain.validators.constraints.NotExistUserName;
import vn.zeus.web.mapper.UserMapper;

public class NotExistUserNameValidator implements ConstraintValidator<NotExistUserName, String> {

	@Autowired
	private UserMapper userMapper;

	@Override
	public void initialize(NotExistUserName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (Objects.isNull(value)) {
			return true;
		}

		User userSelect = new User();
		userSelect.setUserName(value.trim());

		return userMapper.selectOne(userSelect) == null;
	}
}

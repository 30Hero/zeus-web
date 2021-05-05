package vn.zeus.web.domain.validators;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.zeus.web.domain.validators.constraints.UserPassword;

public class UserPasswordValidator implements ConstraintValidator<UserPassword, String> {

	@Override
	public void initialize(UserPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (Objects.isNull(value)) {
			return true;
		}

		return value.matches("^[a-zA-Z0-9._-]+$");
	}
}

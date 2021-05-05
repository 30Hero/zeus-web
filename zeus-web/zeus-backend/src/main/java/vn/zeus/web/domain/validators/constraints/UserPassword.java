package vn.zeus.web.domain.validators.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import vn.zeus.web.domain.validators.UserPasswordValidator;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
		ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserPasswordValidator.class)
public @interface UserPassword {
	String message() default "{zeus.validation.constraints.Password.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

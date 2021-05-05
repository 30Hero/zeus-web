package vn.zeus.web.domain.validators.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import vn.zeus.web.domain.validators.ExistRoleIdValidator;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
		ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ExistRoleIdValidator.class)
public @interface ExistRoleId {
	String message() default "{zeus.validation.constraints.ExistRoleId.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

package vn.zeus.web.domain.validators;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import vn.zeus.web.domain.validators.constraints.AcceptedNumbers;

/**
 * Class implement of
 * {@link vn.com.fumart.domain.validators.constraints.AcceptedNumbers}
 */
public class AcceptedNumbersValidator implements ConstraintValidator<AcceptedNumbers, Number> {

	private List<Long> valueList;

	@Override
	public void initialize(AcceptedNumbers constraintAnnotation) {
		valueList = new ArrayList<Long>();
		for (long val : constraintAnnotation.values()) {
			valueList.add(val);
		}
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		for (long val : valueList) {
			if (val == value.longValue()) {
				return true;
			}
		}
		return false;
	}
}

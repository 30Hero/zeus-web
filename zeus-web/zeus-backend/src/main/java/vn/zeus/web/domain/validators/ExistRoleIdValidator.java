package vn.zeus.web.domain.validators;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import vn.zeus.web.domain.model.Role;
import vn.zeus.web.domain.validators.constraints.ExistRoleId;
import vn.zeus.web.mapper.RoleMapper;

public class ExistRoleIdValidator implements ConstraintValidator<ExistRoleId, Long> {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public void initialize(ExistRoleId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (Objects.isNull(value)) {
			return true;
		}

		Role roleSelect = new Role();
		roleSelect.setId(value);

		return roleMapper.selectOne(roleSelect) != null;
	}
}

package com.github.tiger.pay.validation;

import com.github.tiger.pay.common.sensitive.SensitiveWordFilter;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 非法验证器类
 *
 * @author liuhongming
 */
public class ForbiddenValidator implements ConstraintValidator<Forbidden, String> {

    private SensitiveWordFilter sensitiveWordFilter = new SensitiveWordFilter();

    @Override
    public void initialize(Forbidden constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        if (sensitiveWordFilter.checkSensitiveWord(value)) {
            ((ConstraintValidatorContextImpl) context).getConstraintDescriptor()
                    .getAttributes().put("word", value);
        }

        return true;
    }
}

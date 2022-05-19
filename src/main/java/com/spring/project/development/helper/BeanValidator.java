package com.spring.project.development.helper;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.*;


@Component
public class BeanValidator {
    @Deprecated
    public Map<String, List<String>> Validate(Object entity) {
        Map<String, List<String>> beanValidationMessageCodes = new HashMap<String, List<String>>();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);

        for (final ConstraintViolation<?> violation : violations) {
            List<String> messageArgs = new ArrayList<String>();
            beanValidationMessageCodes.put(violation.getMessageTemplate(), messageArgs);
        }
        return beanValidationMessageCodes;
    }

    public ResponseMessage Validate(Object entity, ResponseMessage responseMessage) {
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);

        for (final ConstraintViolation<?> violation : violations) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText(violation.getMessageTemplate());
        }
        return responseMessage;
    }
}

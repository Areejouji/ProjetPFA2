package com.example.ProjectV1.Validator;

import com.example.ProjectV1.Exception.ObjectNotValidException;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator<T> {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public void validate(T objectToValidate) {
        Set<ConstraintViolation<T>> violation = validator.validate(objectToValidate);
        if (!violation.isEmpty()) {
            var errorMessage = violation
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ObjectNotValidException(errorMessage);
        }

    }
}

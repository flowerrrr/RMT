package de.flower.common.validation.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;
/**
 * Utility class for validation.
 *
 */
public final class Validation {

    private Validation() {}

    /**
     * Does a validation and throws an exception if an error occurs.
     *
     * @param validator the validator
     * @param object the object
     * @throws javax.validation.ConstraintViolationException
     */
    @SuppressWarnings("unchecked")
    public static void validate(final Validator validator, final Object object) {
        final Set<?> violations = validator.validate(object);
        if (violations.isEmpty()) {
            return;
        } else {
            throw new ConstraintViolationException((Set<ConstraintViolation<?>>)violations);
        }
    }

}

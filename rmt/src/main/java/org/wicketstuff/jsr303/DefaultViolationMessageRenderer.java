package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;


public class DefaultViolationMessageRenderer {

    public String renderPropertyViolation(final ConstraintViolation<?> violation) {
        return "'${label}' " + violation.getMessage();
    }

    public String renderBeanViolation(final ConstraintViolation<?> violation) {
        return violation.getMessage();
    }
}

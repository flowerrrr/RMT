package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;

/**
 * @author flowerrrr
 */
public class DefaultViolationMessageRenderer implements ViolationMessageRenderer {

    public String renderPropertyViolation(final ConstraintViolation<?> violation) {
        return "'${label}' " + violation.getMessage();
    }

    public String renderBeanViolation(final ConstraintViolation<?> violation) {
        return violation.getMessage();
    }
}

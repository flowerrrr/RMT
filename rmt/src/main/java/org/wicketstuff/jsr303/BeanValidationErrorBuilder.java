package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;


public class BeanValidationErrorBuilder<T> extends AbstractValidationErrorBuilder<T> {

    public BeanValidationErrorBuilder(final ConstraintViolation<T> violation) {
        super(violation);
    }

    @Override
    protected String render() {
        return getViolationMessageRenderer().renderBeanViolation(violation);
    }
}

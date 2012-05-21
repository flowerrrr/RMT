package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;

/**
 * @author flowerrrr
 */
public class PropertyValidationErrorBuilder<T> extends AbstractValidationErrorBuilder<T> {

    public PropertyValidationErrorBuilder(final ConstraintViolation<T> violation) {
        super(violation);
    }

    @Override
    protected String render() {
        return getViolationMessageRenderer().renderPropertyViolation(violation);
    }

}

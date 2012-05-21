package org.wicketstuff.jsr303;

import com.google.common.base.Preconditions;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.ValidationError;

import javax.validation.ConstraintViolation;

abstract class AbstractValidationErrorBuilder<T> {

    protected final ConstraintViolation<T> violation;

    @SpringBean
    private ViolationMessageRenderer violationMessageRenderer;

    public AbstractValidationErrorBuilder(final ConstraintViolation<T> violation) {
        Preconditions.checkNotNull(violation, "violation");
        this.violation = violation;
        Injector.get().inject(this);
    }

    public ValidationError createError() {
        final ValidationError ve = new ValidationError();
        ve.setMessage(render());
        final String messageTemplate = violation.getMessageTemplate();
        final String key = extractKey(messageTemplate);
        if (key != null) {
            ve.addMessageKey(key);
        }
        return ve;
    }

    protected abstract String render();

    private static String extractKey(final String messageTemplate) {
        Preconditions.checkNotNull(messageTemplate, "messageTemplate");
        final String key = messageTemplate.trim();
        if (key.startsWith("{") && key.endsWith("}")) {
            return key.substring(1, key.length() - 1);
        } else {
            return null;
        }
    }

    public ViolationMessageRenderer getViolationMessageRenderer() {
        return violationMessageRenderer;
    }
}

package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;
import java.io.Serializable;

/**
 * @author flowerrrr
 */
public class ConstraintFilter implements Serializable {

    private String messageTemplate;

    public ConstraintFilter(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public boolean matches(ConstraintViolation<?> violation) {
        return violation.getMessageTemplate().equals(messageTemplate);
    }
}

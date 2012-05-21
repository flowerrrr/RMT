/**
 * 
 */
package org.wicketstuff.jsr303;

import javax.validation.ConstraintViolation;

/**
 * Controls, how to use Messages as resolved by the Validation Framework.
 * 
 * @see DefaultViolationMessageRenderer
 */
public interface ViolationMessageRenderer
{
	String renderPropertyViolation(ConstraintViolation<?> violation);

	String renderBeanViolation(final ConstraintViolation<?> violation);
}

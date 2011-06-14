package de.flower.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Defines a list of fields that are unique for an entity.
 *
 * @see ExtendedEntityHome
 */
@Target( {ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {

    String message() default "{validation.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

	/**
	 * A list of field (property) names.
     * If empty the annotated field is preset.
	 */
	String[] fields() default {};
}
package de.flower.common.validation.unique;

import javax.persistence.UniqueConstraint;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Defines a uniqueness validator.
 * Either the list of fields has to specified or
 * the name of the uniqueness constraint defined in @Table has to be given.
 *
 * @see ExtendedEntityHome
 */
@Target( {ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {

    String message() default "{validation.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * If left empty the constraints are derived from the @Table annotation.
     * @return
     */
	UniqueConstraint[] constraints() default {};


}
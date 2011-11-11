package de.flower.common.validation.unique;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Defines a uniqueness constraint on a table.
 *
 * Either <code>name</code> points to the @UniqueConstraint of the entity or <code>attributeNames</code>
 * must be given to name the column(s) to check.
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {

    String message() default "{validation.unique}";

    /**
     * Name the groups for this constraint. Avoid using the default group cause it is almost always undesired to
     * have database hits when the validator kicks in automatically before saving an entity.
     * Either validate the entity explicitly before saving the entity or rely on the database to throw an error
     * if uniqueness is validated.
     *
     * Use this group in wicket code together with the ComponentBeanValidator to restrict validation to a single uniqueness constraint.
     *
     * @return
     */
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Name of entity class.
     *
     * It would be possible to detect the class at runtime, but that does not justify the extra code to write.
     *
     * @return the class
     */
    Class<?> clazz();

    /**
     * Set to the name of the @UniqueConstraint that should be checked.
     *
     * @return the string
     */
    String name() default "";

    /**
     * The names of the entity attributes that will be checked for uniqueness.
     * @return
     */
    String[] attributeNames() default { };

    /**
     * Defines several {@code @Unique} annotations on the same element.
     */
    @Target({ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Unique[] value();
    }

}
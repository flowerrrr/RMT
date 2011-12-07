package de.flower.common.validation.spring;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Enables usage of spring beans to do validation.
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BeanAssertValidator.class)
@Documented
public @interface BeanAssert {

    String message() default "{validation.property}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Name of spring bean class that does the validation.
     *
     * @return the class
     */
    Class<?> beanClass();

    /**
     * Defines several annotations on the same element.
     */
    @Target({ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        BeanAssert[] value();
    }

}
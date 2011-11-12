package de.flower.common.validation.factory;

import de.flower.common.spring.SpringApplicationContextBridge;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

/**
 * CVFactory that enables use of springs wiring in custom validators. Used for validators that are not
 * created by springs own validation factory bean. hibernates default validation on insert and update
 * does not know about any spring configuration.
 *
 * Since the factory is instantiated before the spring context is alive the factory delays instantiation of
 * the delegated factory until it is needed.
 *
 * This factory is set in META-INF/validation.xml.
 */
public final class ApplicationContextAwareValidationFactory implements ConstraintValidatorFactory {

    private ConstraintValidatorFactory constraintValidatorFactory;

    public ApplicationContextAwareValidationFactory() {
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(final Class<T> key) {
        return getConstraintValidatorFactory().getInstance(key);
    }

    /**
     * Gets the constraint validator factory.
     *
     * @return the constraint validator factory
     */
    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        if (constraintValidatorFactory == null) {
            constraintValidatorFactory = new SpringConstraintValidatorFactory(getBeanFactory());
        }
        return constraintValidatorFactory;
    }

    /**
     * Throws ISE when the application context is not yet available.
     *
     * @return the bean factory
     */
    public AutowireCapableBeanFactory getBeanFactory() {
        return SpringApplicationContextBridge.getInstance().getApplicationContext().getAutowireCapableBeanFactory();
    }
}

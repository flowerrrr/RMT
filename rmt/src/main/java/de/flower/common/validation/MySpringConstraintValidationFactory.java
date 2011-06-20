package de.flower.common.validation;

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
 * Since the factory is instaniated before the spring context is alive the factory delays instantiation of
 * the delegated factory until it is needed.
 *
 * @author oblume
 */
public class MySpringConstraintValidationFactory implements ConstraintValidatorFactory {

    ConstraintValidatorFactory constraintValidatorFactory;

    public MySpringConstraintValidationFactory() {
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        return getConstraintValidatorFactory().getInstance(key);
    }

    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        if (constraintValidatorFactory == null) {
            constraintValidatorFactory = new SpringConstraintValidatorFactory(getBeanFactory());
        }
        return constraintValidatorFactory;
    }

    /**
     * Throws ISE when the application context is not yet available.
     * @return
     */
    public AutowireCapableBeanFactory getBeanFactory() {
        return SpringApplicationContextBridge.getInstance().getApplicationContext().getAutowireCapableBeanFactory();
    }
}

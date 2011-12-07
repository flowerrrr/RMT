package de.flower.common.validation.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for testing unique constraints.
 */
public class BeanAssertValidator implements ConstraintValidator<BeanAssert, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(BeanAssertValidator.class);

    private BeanAssert constraint;

    private IBeanValidator beanValidator;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void initialize(final BeanAssert constraint) {
        this.constraint = constraint;
        beanValidator = (IBeanValidator) applicationContext.getBean(constraint.beanClass());
    }

    @Override
    public boolean isValid(final Object bean, final ConstraintValidatorContext context) {
        LOG.debug("Validating bean [{}] against constraint [{}].", bean, constraint);
        return beanValidator.isValid(bean);
    }

}
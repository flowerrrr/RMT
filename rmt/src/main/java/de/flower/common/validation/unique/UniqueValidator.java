package de.flower.common.validation.unique;

import de.flower.common.model.IEntity;
import de.flower.common.validation.unique.impl.UniqueDef;
import de.flower.common.validation.unique.impl.UniqueDefFactory;
import de.flower.common.util.Check;
import de.flower.common.validation.unique.impl.IColumnResolver;
import de.flower.common.validation.unique.impl.IRowCountChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Validator for testing unique constraints.
 */
public final class UniqueValidator implements ConstraintValidator<Unique, IEntity> {

    private static final Logger LOG = LoggerFactory.getLogger(UniqueValidator.class);

    private final IColumnResolver columnResolver;

    private final IRowCountChecker rowCountChecker;

    private UniqueDef constraintDef;

    @Autowired
    public UniqueValidator(final IColumnResolver columnResolver,
            final IRowCountChecker rowCountChecker) {
        this.columnResolver = Check.notNull(columnResolver);
        this.rowCountChecker = Check.notNull(rowCountChecker);
    }

    @Override
    public void initialize(final Unique constraint) {
        constraintDef = UniqueDefFactory.parseConstraint(constraint, columnResolver);
    }

    @Override
    public boolean isValid(final IEntity entity, final ConstraintValidatorContext context) {
        LOG.debug("Validating [{}] against constraint [{}].", entity, constraintDef);
        boolean valid = true;
        valid = rowCountChecker.rowCount(entity, constraintDef.getAttributeNames()).intValue() == 0;
        if (!valid) {
            context.buildConstraintViolationWithTemplate(
                    "{de.flower.common.validation.constraints.unique.message." + constraintDef.getName() + "}")
                    .addNode(constraintDef.getName()).addConstraintViolation().disableDefaultConstraintViolation();
        }
        return valid;
    }

}
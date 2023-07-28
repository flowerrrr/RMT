package de.flower.common.validation.unique;

import de.flower.common.model.db.entity.IEntity;
import de.flower.common.util.Check;
import de.flower.common.validation.unique.impl.IColumnResolver;
import de.flower.common.validation.unique.impl.RowCountChecker;
import de.flower.common.validation.unique.impl.UniqueDef;
import de.flower.common.validation.unique.impl.UniqueDefFactory;
import org.apache.commons.beanutils.PropertyUtils;
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

    private final RowCountChecker rowCountChecker;

    private UniqueDef constraintDef;

    @Autowired
    public UniqueValidator(final IColumnResolver columnResolver,
            final RowCountChecker rowCountChecker) {
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
        // Unique-Validator does not handle null values correctly
        for (final String attributeName : constraintDef.getAttributeNames()) {
            try {
                final Object value = PropertyUtils.getProperty(entity, attributeName);
                if (value == null) {
                    return true;
                }
            // CHECKSTYLE IGNORE 1 LINES
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
        boolean valid = rowCountChecker.rowCount(entity, constraintDef.getAttributeNames()).intValue() == 0;
        return valid;
    }

}
package de.flower.common.validation.unique.impl;


import de.flower.common.util.Check;
import de.flower.common.validation.unique.Unique;

/**
 * Parses a @Unique constraint and translates it into a UniqueDef object.
 * Does some preprocessing and checking.
 */
public final class UniqueDefFactory {

    private UniqueDefFactory() {}

    /**
     * Validates the constraint annotation and translates the information into a UniqueDef object.
     *
     * @param constraint the constraint
     * @param columnResolver the column resolver used to translate column names to entity attribute names.
     * @return the unique def
     */
    public static UniqueDef parseConstraint(final Unique constraint, final IColumnResolver columnResolver) {
        Check.notNull(constraint);
        Check.notNull(columnResolver);
        UniqueDef constraintDef;
        // check if annotation is correctly set
        if ("".equals(constraint.name())) {
            Check.isTrue(constraint.attributeNames().length != 0, "Either the name of a constraint or the attribute names must be given.");
            constraintDef = new UniqueDef(null, constraint.attributeNames());
        } else {
            String[] attributeNames =  constraint.attributeNames();
            if (attributeNames.length == 0) {
                // read out column names from @UniqueConstraint annotation of entity
                final String[] columnNames = UniqueConstraintDetector.getColumnNames(constraint.name(), constraint.clazz());
                // convert columnNames to entity attributes
                attributeNames = columnResolver.mapColumns2Attributes(constraint.clazz(), columnNames);
            }
            constraintDef = new UniqueDef(constraint.name(), attributeNames);
        }
        Check.isTrue(constraint.groups().length != 0, "Must set validation group in @Unique constraint.");
        return constraintDef;
    }

}

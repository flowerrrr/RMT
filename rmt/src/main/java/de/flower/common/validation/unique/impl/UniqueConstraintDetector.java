package de.flower.common.validation.unique.impl;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The Class UniqueConstraintDetector.
 */
public final class UniqueConstraintDetector {

    private UniqueConstraintDetector() {}

    /**
     * Gets the column names of a @UniqueConstraint with given name.
     *
     * @param constraintName the constraint name
     * @param entityClass the entity class
     * @return the column names
     */
    public static String[] getColumnNames(final String constraintName, final Class<?> entityClass) {
        final Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            for (final UniqueConstraint uniqueConstraint : table.uniqueConstraints()) {
                if (uniqueConstraint.name().equals(constraintName)) {
                    return uniqueConstraint.columnNames();
                }
            }
        }
        throw new IllegalStateException("No @UniqueConstraint with name ["
                + constraintName + "] found in entity class [" + entityClass.getSimpleName() + "].");
    }

}


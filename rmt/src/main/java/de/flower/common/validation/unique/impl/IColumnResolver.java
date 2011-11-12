package de.flower.common.validation.unique.impl;

/**
 * Translates database column names to property field names.
 * Can either be done by accessing hibernates underlying meta model or
 * by using certain conventions when naming columns.
 *
 */
public interface IColumnResolver {


    /**
     * Map column names of entityClass to property names.
     *
     * @param entityClass the entity class
     * @param columnNames the column names
     * @return the string[] attribute names
     */
    String[] mapColumns2Attributes(Class<?> entityClass, String[] columnNames);
}

package de.flower.common.jpa;

/**
 * Translates database column names to property field names.
 * Can either be done by accessing hibernates underlying meta model or
 * by using certain conventions when naming columns.
 *
 * @author flowerrrr
 */
public interface IColumnResolver {


    String[] map2FieldNames(Class<?> entityClass, String[] columnNames);
}

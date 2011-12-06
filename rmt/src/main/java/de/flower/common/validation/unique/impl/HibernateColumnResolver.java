package de.flower.common.validation.unique.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;

/**
 */
@Service
public class HibernateColumnResolver implements IColumnResolver {

    @Autowired
    private EntityManagerFactory emFactory;

    @Override
    public String[] mapColumns2Attributes(Class<?> entityClass, String[] columnNames) {
        String[] fieldNames = new String[columnNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            fieldNames[i] = map2FieldName(entityClass, columnNames[i]);
        }
        return fieldNames;
    }

    public String map2FieldName(Class<?> entityClass, String columnName) {
        ManagedType<?> managedType = emFactory.getMetamodel().managedType(entityClass);
        for (Attribute attr : managedType.getAttributes()) {
            String guessedColumnName = null;
            switch (attr.getPersistentAttributeType()) {
                case BASIC:
                    guessedColumnName = attr.getName(); break;
                case MANY_TO_ONE:
                    guessedColumnName = attr.getName() + "_id";  break;
                case ONE_TO_MANY:
                    // mapping not supported. typically the associated table owns the joining column.
                    break;
                case MANY_TO_MANY:
                    // mapping not supported.
                    break;
                default:
                    throw new RuntimeException("Unsupported mapping type [" + attr.getPersistentAttributeType() + "] for column [" + attr.getName() + "].");
            }
            if (columnName.equals(guessedColumnName)) {
                return attr.getName();
            }
        }
        throw new RuntimeException("Could not resolve columnName [" + columnName + "]");
    }
}
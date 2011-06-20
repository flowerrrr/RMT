package de.flower.common.validation.unique;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oblume
 */
public class UniqueConstraintDetector {

    public static List<UniqueConstraintDef> detectConstraints(Class<?> entityClass) {

        List<UniqueConstraintDef> constraints = new ArrayList<UniqueConstraintDef>();

        // lookup uniquness constraints in @Table annotation of class
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            constraints = convert(table.uniqueConstraints());
        }
        // now look for @Column(unique = true) annotations
        List<Field> fields = findFieldsByAnnotation(entityClass, Column.class);
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column.unique() == true) {
                constraints.add(new UniqueConstraintDef(field.getName()));
            }
        }
        return constraints;
    }

    public static List<UniqueConstraintDef> convert(UniqueConstraint[] uniqueConstraints) {
        List<UniqueConstraintDef> constraints = new ArrayList<UniqueConstraintDef>();
        for (UniqueConstraint uc : uniqueConstraints) {
            constraints.add(new UniqueConstraintDef(uc.name(), uc.columnNames()));
        }
        return constraints;
    }

    private static List<Field> findFieldsByAnnotation(Class<?> clazz, Class<? extends Annotation> annotationType) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<Field>();
        for (Field field : declaredFields) {
            if (field.getAnnotation(annotationType) != null) {
                fields.add(field);
            }
        }
        return fields;
    }
}


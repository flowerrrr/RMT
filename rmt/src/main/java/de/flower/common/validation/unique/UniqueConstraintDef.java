package de.flower.common.validation.unique;

/**
 * @author flowerrrr
 */
public class UniqueConstraintDef {

    public String[] columnNames = new String[]{};

    public String name;

    public UniqueConstraintDef(String fieldName) {
        this.name = fieldName;
        columnNames = new String[]{fieldName};
    }

    public UniqueConstraintDef(String name, String[] columnNames) {
        this.name = name;
        this.columnNames = columnNames;
        if (name == null) {
            this.name = "uc_" + org.apache.commons.lang3.StringUtils.join(columnNames, "_");
        }
    }
}

package de.flower.common.validation.unique;

import org.apache.commons.lang.StringUtils;

/**
 * @author oblume
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
            name = "uc_" + StringUtils.join(columnNames, "_");
        }
    }
}

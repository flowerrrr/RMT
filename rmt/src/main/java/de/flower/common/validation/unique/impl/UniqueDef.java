package de.flower.common.validation.unique.impl;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple container class.
 */
public final class UniqueDef {

    /**
     * Name of entity class attributes that form the uniqueness check.
     */
    private List<String> attributeNames = new ArrayList<String>();

    /**
     * Name of constraint. Is used for picking the right validation message from a resource bundle.
     */
    private String name;

    public UniqueDef(final String attributeName) {
        name = attributeName;
        attributeNames.add(attributeName);
    }

    public UniqueDef(final String name, final String[] attributeNames) {
        this.name = name;
        this.attributeNames = Arrays.asList(attributeNames);
        if (this.name  == null) {
            this.name = "uc_" + StringUtils.arrayToDelimitedString(attributeNames, "_");
        }
    }

    public List<String> getAttributeNames() {
        return attributeNames;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UniqueDef [name=" + name + ", attributeNames=" + attributeNames + "]";
    }
}

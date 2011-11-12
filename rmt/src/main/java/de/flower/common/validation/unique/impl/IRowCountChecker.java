package de.flower.common.validation.unique.impl;

import de.flower.common.model.IEntity;

import java.util.List;

/**
 * Interface for beans performing a row count check.
 */
public interface IRowCountChecker {

    /**
     * Calculate number of entities that are equals with respect to the attributeNames of the given entity.
     *
     * @param entity the entity
     * @param attributeNames the fields
     * @return number of records.
     */
    Long rowCount(IEntity entity, List<String> attributeNames);

}
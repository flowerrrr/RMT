package de.flower.common.model.db.entity;

/**
 * Marker interface for entity classes.
 */
public interface IEntity extends IIdentifiable<Long> {

    boolean isNew();
}

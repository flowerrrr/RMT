package de.flower.common.model;

/**
 * Marker interface for entity classes.
 *
 * @author flowerrrr
 */
public interface IEntity extends IIdentifiable<Long> {

    boolean isNew();
}

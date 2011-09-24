package de.flower.common.model;

/**
 * Marker interface for entity classes.
 * @author flowerrrr
 */
public interface BaseEntity {

    boolean isTransient();

    Long getId();
}

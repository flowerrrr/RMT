package de.flower.common.model;

/**
 * Marker interface for entity classes.
 * @author oblume
 */
public interface BaseEntity {

    boolean isTransient();

    Long getId();
}

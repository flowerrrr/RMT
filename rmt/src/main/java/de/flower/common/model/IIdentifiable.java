package de.flower.common.model;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
public interface IIdentifiable<ID extends Serializable> {

    ID getId();
}

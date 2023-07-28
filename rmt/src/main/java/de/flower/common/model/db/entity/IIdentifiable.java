package de.flower.common.model.db.entity;

import java.io.Serializable;


public interface IIdentifiable<ID extends Serializable> {

    ID getId();
}

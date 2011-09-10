package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author oblume
 */
@Entity
public class Invitation extends AbstractBaseEntity {

    @ManyToOne
    private Event event;

    @ManyToOne
    private Users user;
}

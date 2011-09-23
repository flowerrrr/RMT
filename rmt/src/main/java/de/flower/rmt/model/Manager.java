package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author oblume
 */
@Entity
public class Manager extends AbstractBaseEntity {

    @ManyToOne
    private Team team;

    @ManyToOne
    private User user;

}

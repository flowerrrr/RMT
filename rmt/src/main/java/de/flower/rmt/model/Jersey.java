package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author flowerrrr
 */
@Entity
public class Jersey extends AbstractBaseEntity {

    @Column
    private String shirt;

    @Column
    private String shorts;

    @Column
    private String socks;

    @ManyToOne
    private Team team;

    private Jersey() {
    }
}

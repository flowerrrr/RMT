package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author flowerrrr
 */
@Entity
public class Comment extends AbstractBaseEntity {

    @Column
    private String comment;

    @Column
    private User author;

    @ManyToOne
    private Response response;

}

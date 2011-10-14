package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.rmt.model.event.Event;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author flowerrrr
 */
@Entity
public class Invitation extends AbstractBaseEntity {

    @Column
    @NotNull
    private Date date;

    @Column
    @NotBlank
    private String subject;

    @Column
    @NotBlank
    private String body;

    @ManyToOne
    private Event event;

    private Invitation() {

    }

}

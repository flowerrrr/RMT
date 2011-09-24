package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.rmt.model.event.Event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author oblume
 */
@Entity
public class Response extends AbstractBaseEntity {

    @Column
    @NotNull
    private RSVPStatus status;

    @Column
    @NotNull
    private Date date;

    @OneToMany(mappedBy = "response")
    private List<Comment> comments;

    @Column
    private String guestName;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;
}

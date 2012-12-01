package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author flowerrrr
 */
@Entity
public class EventTeamPlayer extends AbstractBaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_team")
    private EventTeam eventTeam;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_invitation")
    private Invitation invitation;

    @NotNull
    @Column(name = "sort_order")
    private Integer order;

    protected EventTeamPlayer() {
    }

    public EventTeamPlayer(final EventTeam eventTeam, final Invitation invitation) {
        this.eventTeam = eventTeam;
        this.invitation = invitation;
    }

    public EventTeam getEventTeam() {
        return eventTeam;
    }

    public void setEventTeam(final EventTeam team) {
        this.eventTeam = team;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(final Invitation invitation) {
        this.invitation = invitation;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(final Integer order) {
        this.order = order;
    }
}

package de.flower.rmt.model.db.entity;

import com.mysema.query.annotations.QueryInit;
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
public class LineupItem extends AbstractBaseEntity {

    @Column
    private Long absTop;

    @Column 
    private Long absLeft;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @QueryInit("event")
    @Index(name = "ix_lineup")
    private Lineup lineup;

    @NotNull
    @QueryInit("user")
    @ManyToOne(fetch = FetchType.LAZY)
    private Invitation invitation;

    protected LineupItem() {
    }

    public LineupItem(final Lineup lineup, final Invitation invitation) {
        this.lineup = lineup;
        this.invitation = invitation;
    }

    public Long getTop() {
        return absTop;
    }

    public void setTop(final Long top) {
        this.absTop = top;
    }

    public Long getLeft() {
        return absLeft;
    }

    public void setLeft(final Long left) {
        this.absLeft = left;
    }

    public Lineup getLineup() {
        return lineup;
    }

    public void setLineup(final Lineup lineup) {
        this.lineup = lineup;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(final Invitation invitation) {
        this.invitation = invitation;
    }

}

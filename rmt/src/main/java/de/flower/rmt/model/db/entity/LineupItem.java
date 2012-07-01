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

    /**
     * Coordinates are between 0 .. 1. Origin is upper left corner of editor grid.
     */
    @Column
    private Double relLeft;

    @Column
    private Double relTop;

    @Column
    private Long top;

    @Column 
    private Long left;

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

    public Double getRelLeft() {
        return relLeft;
    }

    public void setRelLeft(final Double relLeft) {
        this.relLeft = relLeft;
    }

    public Double getRelTop() {
        return relTop;
    }

    public void setRelTop(final Double relTop) {
        this.relTop = relTop;
    }

    public Long getTop() {
        return top;
    }

    public void setTop(final Long top) {
        this.top = top;
    }


    public void setTop(final long top, final long maxTop) {
        setTop(top);
        setRelTop((double) top / maxTop);
    }

    public Long getLeft() {
        return left;
    }

    public void setLeft(final Long left) {
        this.left = left;
    }

    public void setLeft(final long left, final long maxLeft) {
        setLeft(left);
        setRelLeft((double) left / maxLeft);
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

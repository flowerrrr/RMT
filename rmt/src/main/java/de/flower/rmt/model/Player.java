package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author oblume
 */
@Entity
public class Player extends AbstractBaseEntity {

    @ManyToOne
    private Team team;

    @ManyToOne
    private User user;

    /**
     * If true, player is not required to respond to invitations.
     */
    @Column
    private Boolean optional;

    @Deprecated
    public Player() {
    }

    public Player(Team team, User user) {
        this.team = team;
        this.user = user;
    }

    /**
     * Convenience method.
     *
     * @return
     */
    public String getFullname() {
        return this.user.getFullname();
    }

    /**
     * Convenience method.
     *
     * @return
     */
    public User.Status getStatus() {
        return this.user.getStatus();
    }
}

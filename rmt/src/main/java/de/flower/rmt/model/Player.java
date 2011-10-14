package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author flowerrrr
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
    @NotNull
    private Boolean optional;

    private Player() {
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }
}

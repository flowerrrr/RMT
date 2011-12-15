package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import org.hibernate.annotations.Index;

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
    @Index(name = "ix_team")
    private Team team;

    @ManyToOne
    @Index(name = "ix_user")
    private User user;

    /**
     * If true, player is not required to respond to invitations.
     */
    @Column
    @NotNull
    @Index(name = "ix_optional")
    private Boolean optional;

    /**
     * If false, the player does not receive email-invitations (but can still respond to an event).
     */
    @Column
    @NotNull
    @Index(name = "ix_notification")
    private Boolean notification;

    /**
     * If true, the player will not be invited to any future events.
     */
    @Column
    @NotNull
    @Index(name = "ix_retired")
    private Boolean retired;

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
    @Deprecated // might throw LIE
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

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(final Boolean notification) {
        this.notification = notification;
    }

    public Boolean getRetired() {
        return retired;
    }

    public void setRetired(final Boolean retired) {
        this.retired = retired;
    }
}

package de.flower.rmt.model.db.entity;

import org.hibernate.annotations.Index;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "activity")
public class Activity extends AbstractClubRelatedEntity {

    /**
     * The message to display in the activity stream. A renderer will take care of formatting the message.
     */
    @Basic
    @Column(columnDefinition = "blob")
    @Valid
    private Serializable message;

    @Column
    @NotNull
    @Index(name = "ix_date")
    private Date date;

    /**
     * User that triggered the activity.
     */
    @ManyToOne
    private User user;

    protected Activity() {}

    public Activity(Club club) {
        super(club);
    }

    public Serializable getMessage() {
        return message;
    }

    public void setMessage(final Serializable message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + getId() +
                ", message=" + message +
                ", date=" + date +
                '}';
    }
}

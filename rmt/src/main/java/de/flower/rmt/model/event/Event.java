package de.flower.rmt.model.event;

import de.flower.rmt.model.AbstractClubRelatedEntity;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.Venue;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author oblume
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "eventType",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("Event")
public class Event extends AbstractClubRelatedEntity {

    @ManyToOne
    @NotNull
    private Team team;

    @ManyToOne
    @NotNull
    private Venue venue;

    /**
     * Only the date-part of Date.
     * Modelled as java.util.Date cause this makes handling the field
     * in wicket forms much easier.
     */
    @Column
    @NotNull
    private Date date;

    @Column
    @NotNull
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    private LocalTime time;

    @Column
    @NotBlank @Size(max = 40)
    private String summary;

    @Column @Size(max = 255)
    private String comment;

    @OneToMany(mappedBy = "event")
    private List<Invitation> invitations;

    @Deprecated
    public Event() {
    }

    public Event(Team team) {
        super(team.getClub());
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

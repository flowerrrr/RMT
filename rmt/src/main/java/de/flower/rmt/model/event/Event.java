package de.flower.rmt.model.event;

import de.flower.rmt.model.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "eventType",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("Event")
public class Event extends AbstractClubRelatedEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    /**
     * Can be null. Sometimes events are created before it is clear where they are held.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;

    /**
     * Only the date-part of Date.
     * Modelled as java.util.Date instead of joda-date cause this makes handling the field
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
    @NotBlank @Size(max = 50)
    private String summary;

    @Column @Size(max = 255)
    private String comment;

    @NotNull
    @Column
    private Boolean invitationSent;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    private List<Invitation> invitations = new ArrayList<Invitation>();

    protected Event() {
    }

    public Event(Club club) {
        super(club);
        this.invitationSent = false;
    }

    public Event(Team team) {
        this(team.getClub());
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
        // normalize date and reset to 0:00:00
        this.date = new LocalDate(date).toDate();
    }

    public LocalTime getTime() {
        return this.time;
    }

    public Date getTimeAsDate() {
        return (time == null) ? null : time.toDateTimeToday().toDate();
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    public Boolean isInvitationSent() {
        return invitationSent;
    }

    public void setInvitationSent(final Boolean invitationSent) {
        this.invitationSent = invitationSent;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(final List<Invitation> invitations) {
        this.invitations = invitations;
    }
}

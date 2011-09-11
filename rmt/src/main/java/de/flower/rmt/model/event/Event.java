package de.flower.rmt.model.event;

import de.flower.rmt.model.AbstractClubRelatedEntity;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.Venue;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.util.Arrays;
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
    private Team team;

    @ManyToOne
    private Venue venue;

    /**
     * Only the date-part of DateTime.
     */
    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime date;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    private LocalTime time;

    @OneToMany(mappedBy = "event")
    private List<Invitation> invitations;

    private String comment;

    public Event() {

    }

    public Event(Team team) {
        super(team.getClub());
        this.team = team;
    }

    public static List<Class<? extends Event>> getEventTypes() {
        return Arrays.asList(Match.class, Training.class, Tournament.class, Event.class);
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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSummary() {
        return "not implemented";
    }

    public String getTypeResourceKey() {
        return getTypeResourceKey(this.getClass());
    }

    public static String getTypeResourceKey(Class<? extends Event> clazz) {
        return "event.type." + clazz.getSimpleName().toLowerCase();
    }
}
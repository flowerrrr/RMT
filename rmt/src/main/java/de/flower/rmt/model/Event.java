package de.flower.rmt.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.util.List;

/**
 * @author oblume
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "eventType",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("Event")
public class Event extends AbstractClubRelatedEntity {

    @ManyToOne
    private Venue venue;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime date;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private LocalTime meetingTime;

    @Column
    private boolean allDay;

    @OneToMany(mappedBy = "event")
    private List<Invitation> invitations;

    private String comment;

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

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public LocalTime getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(LocalTime meetingTime) {
        this.meetingTime = meetingTime;
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
}

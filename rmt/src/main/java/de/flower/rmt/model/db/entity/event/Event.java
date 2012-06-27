package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.*;
import de.flower.rmt.model.db.type.EventType;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
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
    @Index(name = "ix_team")
    private Team team;

    /**
     * Can be null. Sometimes events are created before it is clear where they are held.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;

    /**
     * Two separate fields to make form validation easier.
     * Modelled as java.util.Date instead of joda-date cause this makes handling the field
     * in wicket forms much easier. Also required to have validation constraint for datepicker.
     * Time part of this field is always midnight 00:00:00.
     */
    @NotNull
    @Transient
    private Date date;

    /**
     * Only the time part of the meeting time.
     */
    @NotNull
    @Transient
    private LocalTime time;

    /**
     * Derived field. Mostly used when searching for event by date and ordering by date.
     * Field is updated whenever #setDate() or #setTime() is called.
     */
    @Column
    @NotNull
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @Index(name = "ix_datetime")
    private DateTime dateTime;

    /**
     * Optional field. Needed for iCalender objects
     */
    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime dateTimeEnd;

    @Column
    @NotBlank
    @Size(max = 50)
    private String summary;

    @Column
    @Size(max = 255)
    private String comment;

    @NotNull
    @Column
    @Index(name = "ix_invitationsent")
    private Boolean invitationSent;

    @Column
    @Index(name = "ix_canceled")
    private Boolean canceled = false;

    @ManyToOne
    @NotNull
    private User createdBy;

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

    // **************************************************
    // Date functions
    // **************************************************
    @Deprecated // only used for wicket form
    public Date getDate() {
        // must return raw value, otherwise validator would fail.
        return date;
    }

    /**
     * Setter used by datepicker field.
     */
    @Deprecated // use #setDateTime if called from user code.
    public void setDate(Date date) {
        // normalize to midnight
        this.date = (date == null) ? null : new LocalDate(date).toDate();
        updateDateTime(this.date);
    }

    @Deprecated // only used for wicket form
    public LocalTime getTime() {
        // must return raw value, otherwise validator would fail.
        return time;
    }

    /**
     * Setter for wicket form component
     */
    @Deprecated // use #setDateTime if called from user code.
    public void setTime(LocalTime time) {
        this.time = time;
        updateDateTime(time);
    }

    /**
     * Returns full date of event. date + time
     *
     * @return
     */
    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final DateTime dateTime) {
        this.dateTime = dateTime;
        if (dateTime == null) {
            this.date = null;
            this.time = null;
        } else {
            this.date = dateTime.toLocalDate().toDate();
            this.time = dateTime.toLocalTime();
        }
    }

    /**
     * Used for wicket date fields.
     *
     * @return
     */
    public Date getDateTimeAsDate() {
        return (dateTime == null) ? null : dateTime.toDate();
    }

    private void updateDateTime(Date date) {
        if (date == null) {
            dateTime = null;
        } else {
            if (time == null) {
                dateTime = new DateTime(date);
            } else {
                dateTime = new DateTime(date).withFields(time);
            }
        }
    }

    private void updateDateTime(LocalTime time) {
        if (time == null) {
            dateTime = null;
        } else {
            if (date == null) {
                dateTime = new DateTime(0).withFields(time);
            } else {
                dateTime = new DateTime(date).withFields(time);
            }
        }
    }

    /**
     * called before object is used in edit form.
     */
    public void initTransientFields() {
        setDateTime(getDateTime());
    }

    public DateTime getDateTimeEnd() {
        if (dateTimeEnd == null & getDateTime() != null && getEventType() != null) {
             // guess duration of event.
             return getDateTime().plusMinutes(getEventType().getDurationMinutes());
         }  else {
            return dateTimeEnd;
        }
    }

    public void setDateTimeEnd(final DateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    // **************************************************
    // End Date functions
    // **************************************************

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

    public boolean isInvitationSent() {
        return invitationSent;
    }

    public void setInvitationSent(final Boolean invitationSent) {
        this.invitationSent = invitationSent;
    }

    /**
     * association might not be initialized after loading an event when query is build
     * using Event_.invitations. This variable can have different values depending
     * on startup behavior of app (based on Match, Tournament or Training).
     *
     * @return
     */
    @Deprecated
    public List<Invitation> getInvitations() {
        return invitations;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public boolean isCanceled() {
        // database field might be null as column was added later
        return canceled == null ? false : canceled;
    }

    public void setCanceled(final boolean canceled) {
        this.canceled = canceled;
    }

    public void setCreatedBy(final User createdBy) {
        this.createdBy = createdBy;
    }

    public EventType getEventType() {
        return EventType.from(this);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + getId() +
                ", date=" + date +
                ", time=" + time +
                ", summary='" + summary + '\'' +
                '}';
    }

}

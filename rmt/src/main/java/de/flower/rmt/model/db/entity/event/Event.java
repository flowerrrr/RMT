package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.AbstractClubRelatedEntity;
import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Opponent;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.model.db.type.EventType;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "event")
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
     * Will be translated to dateTimeEnd by service layer when saved.
     */
    // @NotNull
    @Transient
    private LocalTime timeEnd;

    /**
     * Derived field. Mostly used when searching for event by date and ordering by date.
     * Field is updated whenever #setDate() or #setTime() is called.
     */
    @Column
    @NotNull
    @Index(name = "ix_datetime")
    private Date dateTime;

    /**
     * Optional field. Needed for iCalender objects
     */
    @Column
    private Date dateTimeEnd;

    @Column
    @NotBlank
    @Size(max = 70)
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

    /**
     * Defined here to be able to eager fetch this association with query dsl.
     * Can be null. Sometimes events are created without knowing who the opponent will be.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Opponent opponent;



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

    public void copyFrom(final Event event) {
        setTeam(event.getTeam());
        setDateTime(event.getDateTime());
        setDateTimeEnd(event.getDateTimeEnd());
        setVenue(event.getVenue());
        _setOpponent(_getOpponent());
        setSummary(event.getSummary());
        setComment(event.getComment());
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
        return dateTime == null ? null : new DateTime(dateTime);
    }

    public void setDateTime(final DateTime dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.toDate();
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
        return dateTime;
    }

    private void updateDateTime(Date date) {
        if (date == null) {
            dateTime = null;
        } else {
            if (time == null) {
                dateTime = date;
            } else {
                dateTime = new DateTime(date).withFields(time).toDate();
            }
        }
    }

    private void updateDateTime(LocalTime time) {
        if (time == null) {
            dateTime = null;
        } else {
            if (date == null) {
                dateTime = new DateTime(0).withFields(time).toDate();
            } else {
                dateTime = new DateTime(date).withFields(time).toDate();
            }
        }
    }

    /**
     * called before object is used in edit form.
     */
    public void initTransientFields() {
        setDateTime(getDateTime());
        setDateTimeEnd(getDateTimeEnd());
    }

    public DateTime getDateTimeEnd() {
        if (dateTimeEnd == null & getDateTime() != null && getEventType() != null) {
             // guess duration of event.
             return getDateTime().plusMinutes(getEventType().getMeetBeforeKickOffMinutes() + getEventType().getDurationMinutes());
         }  else {
            return new DateTime(dateTimeEnd);
        }
    }

    public void setDateTimeEnd(final DateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd == null ? null : dateTimeEnd.toDate();
        if (this.dateTimeEnd == null) {
            this.timeEnd = null;
        } else {
            this.timeEnd = dateTimeEnd.toLocalTime();
        }
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(final LocalTime timeEnd) {
        this.timeEnd = timeEnd;
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

    @Deprecated // should only be called from Match instances
    protected Opponent _getOpponent() {
        return opponent;
    }

    @Deprecated // should only be called from Match instances
    protected void _setOpponent(final Opponent opponent) {
        this.opponent = opponent;
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

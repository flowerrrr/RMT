package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import org.hibernate.annotations.Index;

import javax.mail.internet.InternetAddress;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SuppressWarnings("FieldCanBeLocal")
@Entity
@Table(name = "invitation")
public class Invitation extends AbstractBaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.rsvpstatus.notnull}")
    @Index(name = "ix_status")
    private RSVPStatus status = RSVPStatus.NORESPONSE;

    /**
     * Date of response. Might be updated when user changes his status.
     */
    @Column
    @Index(name = "ix_date")
    private Date date;

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.REMOVE)
    @OrderBy("createDate")
    private List<Comment> comments = new ArrayList<>();

    /**
     * First comment of context user.
     * Used for ui forms. Initialized when invitation is loaded.
     */
    @Transient
    @Size(max = Comment.MAXLENGTH)
    private String comment;

    @Column
    @Size(max = 50)
    private String guestName;

    @NotNull
    @Column
    @Index(name = "ix_invitationsent")
    private Boolean invitationSent = false;

    @Column
    @Index(name = "ix_invitationsentdate")
    private Date invitationSentDate;

    @Column
    @Index(name = "ix_noresponseremindersent")
    private Boolean noResponseReminderSent = false;

    @Column
    @Index(name = "ix_noresponseremindersentdate")
    private Date noResponseReminderSentDate;

    @Column
    @Index(name = "ix_unsureremindersent")
    private Boolean unsureReminderSent = false;

    @Column
    @Index(name = "ix_unsureremindersentdate")
    private Date unsureReminderSentDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_event")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_user")
    private User user;

    protected Invitation() {
    }

    public Invitation(Event event, User user) {
        this.user = user;
        this.event = event;
    }

    public Invitation(final Event event, final String guestName) {
        this.event = event;
        this.guestName = guestName;
    }

    public RSVPStatus getStatus() {
        return status;
    }

    public void setStatus(RSVPStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Boolean isInvitationSent() {
        return invitationSent;
    }

    public void setInvitationSent(final Boolean invitationSent) {
        this.invitationSent = invitationSent;
    }

    public Date getInvitationSentDate() {
        return invitationSentDate;
    }

    public Boolean isUnsureReminderSent() {
        return unsureReminderSent;
    }

    public Boolean isNoResponseReminderSent() {
        return noResponseReminderSent;
    }

    public String getName() {
        if (user == null) {
            // TODO (flowerrrr - 04.04.12) marking the name should be done in ui!
            return guestName + " (G)";
        } else {
            return user.getFullname();
        }
    }

    public String getEmail() {
        if (user == null) {
            throw new IllegalStateException("Call #isEmail before accessing email-field.");
        } else {
            return user.getEmail();
        }
    }

    public String[] getEmails() {
        if (user == null) {
            throw new IllegalStateException("Call #isEmail before accessing email-field.");
        } else {
            return user.getEmails();
        }
    }

    public boolean hasEmail() {
        return user != null;
    }

    public InternetAddress getInternetAddress() {
        if (user == null) {
            throw new IllegalStateException("Call #isEmail before accessing email-field.");
        } else {
            return user.getInternetAddress();
        }
    }

    public InternetAddress[] getInternetAddresses() {
        if (user == null) {
            throw new IllegalStateException("User required to lookup internet addresses.");
        }
        return user.getInternetAddresses();
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + getId() +
                ", status=" + status +
                ", date=" + date +
                "}";
    }

}

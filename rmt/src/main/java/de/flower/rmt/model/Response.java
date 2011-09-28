package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.rmt.model.event.Event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
@Entity
public class Response extends AbstractBaseEntity {

    @Column
    @NotNull
    private RSVPStatus status;

    @Column
    @NotNull
    private Date date;

    @OneToMany(mappedBy = "response")
    private List<Comment> comments;

    @Column
    private String guestName;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Player player;

    @Deprecated
    public Response() {

    }

    public Response(Player player, Event event) {
        this.player = player;
        this.event = event;
        this.date = new Date();
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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getName() {
        if (player == null) {
            return guestName;
        } else {
            return player.getFullname();
        }
    }
}

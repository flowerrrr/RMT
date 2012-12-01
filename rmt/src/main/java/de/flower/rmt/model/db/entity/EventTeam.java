package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import de.flower.rmt.model.db.entity.event.Event;
import org.hibernate.annotations.Index;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Team for single event. Used to model Socca5 events.
 *
 * @author flowerrrr
 */
@SuppressWarnings("FieldCanBeLocal")
@Entity
public class EventTeam extends AbstractBaseEntity {

    @NotNull
    @Column
    @Size(max = 40)
    private String name;

    @Column
    private Integer rank;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_event")
    private Event event;

    @OneToMany(mappedBy = "eventTeam", cascade = CascadeType.REMOVE)
    @OrderBy("order")
    private List<EventTeamPlayer> players = new ArrayList<>();

    protected EventTeam() {
    }

    public EventTeam(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(final Integer rank) {
        this.rank = rank;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<EventTeamPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(final List<EventTeamPlayer> players) {
        this.players = players;
    }
}

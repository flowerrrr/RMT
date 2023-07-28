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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Team for single event. Used to model Socca5 events.
 */
@SuppressWarnings("FieldCanBeLocal")
@Entity
@Table(name = "eventteam")
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

    public void addPlayer(final EventTeamPlayer player, final EventTeamPlayer insertBeforePlayer) {
        if (insertBeforePlayer == null) {
            // add to end of player list.
            player.setOrder(players.size());
            players.add(player);
        } else {
            int index = players.indexOf(insertBeforePlayer);
            player.setOrder(insertBeforePlayer.getOrder());
            for (int i = index; i < players.size(); i++) {
                EventTeamPlayer p = players.get(i);
                p.setOrder(p.getOrder() + 1);
            }
            players.add(index, player);
        }
    }

    public void removePlayer(final EventTeamPlayer player) {
        int index = players.indexOf(player);
        players.remove(index);
        // from i on decrement order
        for (int i = index; i < players.size(); i++) {
            EventTeamPlayer p = players.get(i);
            p.setOrder(p.getOrder() - 1);
        }
    }
}

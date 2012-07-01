package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import de.flower.rmt.model.db.entity.event.Event;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
@Entity
public class Lineup extends AbstractBaseEntity {

    @Column
    @Size(max = 20)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_event")
    private Event event;

    @OneToMany(mappedBy = "lineup", cascade = CascadeType.REMOVE)
    private List<LineupItem> items = new ArrayList<LineupItem>();

    protected Lineup() {
    }

    public Lineup(final Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }

    public List<LineupItem> getItems() {
        return items;
    }

    public void setItems(final List<LineupItem> items) {
        this.items = items;
    }
}

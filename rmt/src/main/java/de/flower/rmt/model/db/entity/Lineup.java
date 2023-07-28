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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "lineup")
public class Lineup extends AbstractBaseEntity {

    @Column(nullable = false)
    private Boolean published;

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
        published = false;
    }

    public Lineup(final Event event) {
        this();
        this.event = event;
    }

    public Boolean isPublished() {
        return published;
    }

    public void setPublished(final Boolean published) {
        this.published = published;
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

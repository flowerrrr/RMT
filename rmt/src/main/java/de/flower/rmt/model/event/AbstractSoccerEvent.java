package de.flower.rmt.model.event;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Jersey;
import de.flower.rmt.model.Surface;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
@MappedSuperclass
public abstract class AbstractSoccerEvent extends Event {

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    private LocalTime kickOff;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Surface", joinColumns = @JoinColumn(name = "event_id"))
    private List<Surface> surfaceList = new ArrayList<Surface>();

    /**
     * Can be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Jersey jersey;


    public AbstractSoccerEvent(final Club club) {
        super(club);
    }

    protected AbstractSoccerEvent() {
        super();
    }

    public LocalTime getKickOff() {
        return kickOff;
    }

    public void setKickOff(final LocalTime kickOff) {
        this.kickOff = kickOff;
    }

    public Jersey getJersey() {
        return jersey;
    }

    public void setJersey(final Jersey jersey) {
        this.jersey = jersey;
    }

    public List<Surface> getSurfaceList() {
        return surfaceList;
    }

    public void setSurfaceList(final List<Surface> surfaceList) {
        this.surfaceList = surfaceList;
    }
}

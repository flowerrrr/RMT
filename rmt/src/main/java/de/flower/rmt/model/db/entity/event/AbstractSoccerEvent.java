package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.model.db.type.Surface;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@MappedSuperclass
public abstract class AbstractSoccerEvent extends Event {

    /**
     * Will be stored in database as millis from 1.1.1970. Shows one hour more than actual time due to conversion to UTC.
     * See RMT-669.
     */
    @Column
    @NotNull
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    private LocalTime kickoff;

    /**
     * Map list of surfaces to a string. Not done as @ElementCollection cause it causes trouble with DotNode.getDataType() when
     * used in a subperclass like here.
     */
    @Basic
    @Type(type = "de.flower.rmt.model.db.type.SurfaceListType")
    private List<Surface> surfaceList = new ArrayList<Surface>();

    /**
     * Can be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Uniform uniform;

    public AbstractSoccerEvent(final Club club) {
        super(club);
    }

    protected AbstractSoccerEvent() {
        super();
    }

    @Override
    public void copyFrom(Event event) {
        super.copyFrom(event);
        AbstractSoccerEvent asEvent = (AbstractSoccerEvent) event;
        setKickoff(asEvent.getKickoff());
        setUniform(asEvent.getUniform());
        setSurfaceList(asEvent.getSurfaceList());
    }

    public LocalTime getKickoff() {
        return kickoff;
    }

    public void setKickoff(final LocalTime kickoff) {
        this.kickoff = kickoff;
    }

    public Date getKickoffAsDate() {
        return (kickoff == null) ? null : kickoff.toDateTimeToday().toDate();
    }

    public Uniform getUniform() {
        return uniform;
    }

    public void setUniform(final Uniform uniform) {
        this.uniform = uniform;
    }

    public List<Surface> getSurfaceList() {
        return surfaceList;
    }

    public void setSurfaceList(final List<Surface> surfaceList) {
        this.surfaceList = surfaceList;
    }
}

package de.flower.rmt.model.event;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Surface;
import de.flower.rmt.model.Uniform;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
@MappedSuperclass
public abstract class AbstractSoccerEvent extends Event {

    @Column
    @NotNull
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    private LocalTime kickoff;

    /**
     * Map list of surfaces to a string. Not done as @ElementCollection cause it causes trouble with DotNode.getDataType() when
     * used in a subperclass like here.
     * Also not implemented as hibernate usertype cause user type is just a pain in the ass.
     * Much faster to do conversion here manually in code.
     */
    @Basic
    @Type(type = "de.flower.rmt.model.type.SurfaceListType")
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

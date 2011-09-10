package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @author oblume
 */
@MappedSuperclass
public abstract class AbstractClubRelatedEntity extends AbstractBaseEntity {

    @NotNull
    @ManyToOne
    private Club club;

    protected AbstractClubRelatedEntity() {
    }

    protected AbstractClubRelatedEntity(Club club) {
        this.club = club;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}

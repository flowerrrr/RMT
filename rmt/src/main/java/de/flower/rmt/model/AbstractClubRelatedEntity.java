package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import org.hibernate.annotations.Index;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @author flowerrrr
 */
@MappedSuperclass
public abstract class AbstractClubRelatedEntity extends AbstractBaseEntity {

    @NotNull
    @ManyToOne
    @Index(name = "ix_club")
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

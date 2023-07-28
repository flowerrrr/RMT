package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import de.flower.common.util.Check;
import org.hibernate.annotations.Index;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;


@MappedSuperclass
public abstract class AbstractClubRelatedEntity extends AbstractBaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_club")
    private Club club;

    protected AbstractClubRelatedEntity() {
    }

    protected AbstractClubRelatedEntity(Club club) {
        this.club = Check.notNull(club);
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}

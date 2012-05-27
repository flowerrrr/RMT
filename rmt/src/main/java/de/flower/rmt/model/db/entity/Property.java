package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

/**
 * @author flowerrrr
 */
@Entity
public class Property extends AbstractBaseEntity {

    /**
     * could not use AbstractClubRelatedEntity cause need nullable association.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_club")
    private Club club;

    @NotBlank
    @Size(max = 80)
    @Column
    private String key;

    @Size(max = 255)
    @Column
    private String value;

    protected Property() {
    }

    public Property(Club club) {
        this.club = club;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}

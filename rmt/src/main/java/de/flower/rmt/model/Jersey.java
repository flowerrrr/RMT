package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author flowerrrr
 */
@Entity
public class Jersey extends AbstractBaseEntity {

    @Column
    @NotBlank
    @Size(max = 50)
    private String shirt;

    @Column
    @NotBlank
    @Size(max = 50)
    private String shorts;

    @Column
    @NotBlank
    @Size(max = 50)
    private String socks;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_team")
    private Team team;

    protected Jersey() {
    }
}

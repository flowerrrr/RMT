package de.flower.rmt.model.db.entity;

import de.flower.common.validation.unique.Unique;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * Opponents for matches.
 *
 * @author flowerrrr
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "name", columnNames = {"name", "club_id"}))
@Unique(name = "name",
        clazz = Opponent.class,
        message = Opponent.Validation.nameNotUniqueMessage,
        groups = { Opponent.Validation.INameUnique.class, Default.class })
public class Opponent extends AbstractClubRelatedEntity {

    public interface Validation {

        public interface INameUnique {}

        public final static String nameNotUniqueMessage = "{validation.unique.name}";
    }

    @NotBlank
    @Size(max = 50)
    @Column
    private String name;

    @URL
    @Size(max = 255)
    @Column
    private String url;

    protected Opponent() {
    }

    public Opponent(Club club) {
        super(club);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}

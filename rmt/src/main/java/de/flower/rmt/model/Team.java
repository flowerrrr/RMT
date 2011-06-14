package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.common.validation.Unique;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author oblume
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "club_id"}))
public class Team extends AbstractBaseEntity {

    @NotBlank
    @Size(max = 40)
    @Unique(fields = {"name", "club"})
    @Column
    private String name;

    @URL
    @Size(max = 255)
    @Column
    private String url;

    @NotNull
    @ManyToOne
    private Club club;

//    @ManyToMany
//    private Set<Users> managers;

    public Team() {

    }

    public Team(String name, String url, Club club) {
        this.name = name;
        this.url = url;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
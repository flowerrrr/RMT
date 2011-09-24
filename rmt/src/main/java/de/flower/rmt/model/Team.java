package de.flower.rmt.model;

import de.flower.common.validation.unique.Unique;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.List;

/**
 * @author oblume
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "name", columnNames = {"name", "club_id"}))
@Unique(groups = { Unique.class, Default.class })
public class Team extends AbstractClubRelatedEntity  {

    @NotBlank
    @Size(max = 40)
    @Column
    private String name;

    @URL
    @Size(max = 255)
    @Column
    private String url;

    @ManyToMany
    @JoinTable(
        name="Manager",
        joinColumns=@JoinColumn(name="team_id"),
        inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private List<User> managers;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @Deprecated
    public Team() {
    }

    public Team(Club club) {
        super(club);
    }

    public Team(String name, String url, Club club) {
        super(club);
        this.name = name;
        this.url = url;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}

package de.flower.rmt.model;

import de.flower.common.validation.unique.Unique;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "name", columnNames = {"name", "club_id"}))
@Unique(name = "name",
        clazz = Team.class,
        message = Team.Validation.nameNotUniqueMessage,
        groups = { Team.Validation.INameUnique.class, Default.class })
public class Team extends AbstractClubRelatedEntity  {

    public static class Validation {

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

    @ManyToMany
    @JoinTable(
        name="Manager",
        joinColumns=@JoinColumn(name="team_id"),
        inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private List<User> managers = new ArrayList<User>();

    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<Player>();

    private Team() {
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

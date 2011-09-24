package de.flower.rmt.repository.impl;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.repository.IUserRepoEx;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author flowerrrr
 */
public class UserRepo extends BaseRepository implements IUserRepoEx {

    private EntityManager em;

    public UserRepo(JpaEntityInformation jpaEntityInformation, EntityManager entityManager) {
        super(jpaEntityInformation, entityManager);
        this.em = entityManager;
    }

    @Override
    public List<User> findUnassignedPlayers(final Team team, Club club) {
        // select from User u where u.club = :club and u not in (select from User u left join Player where p.team = :team)
        final Query query = em.createQuery("select u from User u where u.club = :club and u not in (select u2 from Player p join p.user u2 where p.team = :team)");
        query.setParameter("team", team);
        query.setParameter("club", club);
        return query.getResultList();

    }
}

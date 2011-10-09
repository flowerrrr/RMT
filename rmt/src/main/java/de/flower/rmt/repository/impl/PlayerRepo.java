package de.flower.rmt.repository.impl;

import de.flower.rmt.model.Player;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.repository.IPlayerRepoEx;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author flowerrrr
 */
public class PlayerRepo extends BaseRepository implements IPlayerRepoEx {

    private EntityManager em;

    public PlayerRepo(JpaEntityInformation jpaEntityInformation, EntityManager entityManager) {
        super(jpaEntityInformation, entityManager);
        this.em = entityManager;
    }

    @Override
    public List<Player> findNotResponded(Event event) {
        final Query query = em.createQuery("select p from Player p where p.team = :team and p.optional = false and p not in (select r.player from Response r join r.player where r.event = :event)");
        query.setParameter("team", event.getTeam());
        query.setParameter("event", event);
        return query.getResultList();
    }
}

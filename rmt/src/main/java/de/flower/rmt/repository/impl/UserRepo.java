package de.flower.rmt.repository.impl;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Team;
import de.flower.rmt.model.User;
import de.flower.rmt.repository.IUserRepoEx;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author oblume
 */
public class UserRepo extends BaseRepository implements IUserRepoEx {

    public UserRepo(JpaEntityInformation jpaEntityInformation, EntityManager entityManager) {
        super(jpaEntityInformation, entityManager);
    }

    @Override
    public List<User> findUnassignedPlayers(final Team team, Club club) {
        throw new UnsupportedOperationException("Feature not implemented!");
//        Specification hasClub = Specs.eq(User_.club, club);
//
//
//        // Specification notInTeam = Specs.not(Specs.joinEq(User_.players, Player_.team, team));
//        Specification notInTeam = new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                final ListJoin<User, Player> join = root.join(User_.players);
//                return cb.or(cb.notEqual(join.get(Player_.team), team),
//                        cb.isNull(join.get(Player_.team)) /* matches all users not assigned to any team */);
//            }
//        };
//        List<User> list = userRepo.findAll(Specs.and(hasClub, notInTeam));
//        return list;
    }
}

package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.User;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IUserRepo extends IRepository<User, Long>, IUserRepoEx {

    User findByEmail(String username);

    List<User> findByClub(Club club);

}

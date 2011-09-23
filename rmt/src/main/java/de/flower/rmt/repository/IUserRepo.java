package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author oblume
 */
public interface IUserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, IUserRepoEx {

    User findByEmail(String username);

    List<User> findByClub(Club club);
}

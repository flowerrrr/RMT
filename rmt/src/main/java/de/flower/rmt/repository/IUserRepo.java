package de.flower.rmt.repository;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author oblume
 */
public interface IUserRepo extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    Users findByUsername(String username);

    List<Users> findByClub(Club club);
}

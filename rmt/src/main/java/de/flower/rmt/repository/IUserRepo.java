package de.flower.rmt.repository;

import de.flower.rmt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author oblume
 */
public interface IUserRepo extends JpaRepository<Users, Long> {
}

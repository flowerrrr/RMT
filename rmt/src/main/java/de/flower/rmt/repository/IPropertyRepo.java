package de.flower.rmt.repository;

import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.Property;
import de.flower.rmt.model.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author flowerrrr
 */
public interface IPropertyRepo extends JpaRepository<Property, Long> {

    Property findByClubAndName(Club club, String name);

    Property findByUserAndName(User user, String name);
}

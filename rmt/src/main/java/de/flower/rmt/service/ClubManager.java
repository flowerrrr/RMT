package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.repository.IClubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author flowerrrr
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ClubManager implements IClubManager {

    @Autowired
    private IClubRepo clubRepo;

    @Override
    public List<Club> findAllClubs() {
        return clubRepo.findAll();
    }
}

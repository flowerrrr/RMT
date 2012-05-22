package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Club;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IClubManager {

    List<Club> findAllClubs();

}

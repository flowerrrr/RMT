package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.Uniform;

import java.util.List;

/**
 * @author flowerrrr
 */
public interface IUniformManager {

    void save(Uniform entity);

    Uniform loadById(Long id);

    List<Uniform> findAll();

    List<Uniform> findAllByTeam(Team team);

    void delete(Long id);

    Uniform newInstance(Team team);

}

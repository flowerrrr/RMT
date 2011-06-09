package de.flower.rmt.dao;

import de.flower.rmt.model.MyTeamBE;

import java.util.List;

/**
 * @author oblume
 */
public interface IMyTeamDao {

    void save(MyTeamBE entity);

    MyTeamBE loadById(Long id);

    List<MyTeamBE> loadAll();

    void delete(MyTeamBE entity);
}

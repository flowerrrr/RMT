package de.flower.rmt.service;

import de.flower.rmt.model.MyTeamBE;

import java.util.List;

/**
 * @author oblume
 */
public interface IMyTeamManager {

    void save(MyTeamBE entity);

    List<MyTeamBE> loadAll();
}

package de.flower.rmt.service;

import de.flower.rmt.model.MyTeamBE;

import java.util.List;

/**
 * @author oblume
 */
public interface IMyTeamManager {

    List<MyTeamBE> loadAll();
}

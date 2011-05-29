package de.flower.rmt.dao;

import de.flower.rmt.model.TestBE;

/**
 * @author oblume
 */
public interface ITestDao {

    void save(TestBE entity);

    TestBE loadById(Long id);




}

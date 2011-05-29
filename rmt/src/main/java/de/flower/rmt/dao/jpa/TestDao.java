package de.flower.rmt.dao.jpa;

import de.flower.rmt.dao.ITestDao;
import de.flower.rmt.model.TestBE;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author oblume
 */
@Transactional
@Repository
public class TestDao implements ITestDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void save(TestBE entity) {
        em.persist(entity);
        // em.close();
    }


    @Override
    public TestBE loadById(Long id) {
        return em.find(TestBE.class, id);
    }
}

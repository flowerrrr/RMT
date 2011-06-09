package de.flower.rmt.service

import de.flower.test.AbstractIntegrationTests
import org.testng.annotations.Test
import de.flower.rmt.model.MyTeamBE
import org.springframework.test.annotation.NotTransactional
/**
 * 
 * @author oblume
 */

class MyTeamManagerTest extends AbstractIntegrationTests {

    @Test
    @NotTransactional
    def testSave() {
        var entity = new MyTeamBE()
        entity setName "Juve Amateure"
        myTeamManager.save(entity)

        var id = entity.getId()
        entity = myTeamDao.loadById(id)
        logger info entity

        var status = transactionManager.getTransaction(null)
        entity = myTeamDao.loadById(id)
        myTeamDao delete entity
        transactionManager.commit(status)

        entity = myTeamDao.loadById(id)
        assert(entity == null)


    }

    @Test
    def testLoadAll() {

    }

}
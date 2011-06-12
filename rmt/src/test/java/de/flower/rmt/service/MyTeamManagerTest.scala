package de.flower.rmt.service

import de.flower.test.AbstractIntegrationTests
import org.testng.annotations.Test
import de.flower.rmt.model.TeamBE
import org.springframework.test.annotation.NotTransactional
import org.springframework.beans.factory.annotation.Autowired
import org.testng.Assert._
import javax.validation.{ConstraintViolation, Validator, ConstraintViolationException}
import scala.collection.JavaConversions._

/**
 *
 * @author oblume
 */

class MyTeamManagerTest extends AbstractIntegrationTests {

    @Autowired
    var validator: Validator = _

    @Test
    @NotTransactional
    def testSave() {
        var entity = new TeamBE()
        entity setName "Juve Amateure"
        myTeamManager.save(entity)

        var id = entity.getId()
        entity = myTeamRepo.findOne(id)
        log info "" + entity

        var status = transactionManager.getTransaction(null)
        entity = myTeamRepo.findOne(id)
        myTeamRepo delete entity
        transactionManager.commit(status)

        entity = myTeamRepo.findOne(id)
        assert(entity == null)


    }

    @Test
    def testValidation() {
        var entity = new TeamBE()

        intercept[ConstraintViolationException] {
            myTeamManager save entity
        }

        entity setName "     "
        var violations = validator.validate(entity)
        assertEquals(1, violations.size())

        var violation: ConstraintViolation[TeamBE] = violations.toList.head
        assertEquals(entity.getName(), violation.getInvalidValue())
        log info "" + violation.getConstraintDescriptor


    }

}
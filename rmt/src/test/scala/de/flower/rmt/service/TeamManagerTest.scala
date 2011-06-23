package de.flower.rmt.service

import de.flower.test.AbstractIntegrationTests
import org.testng.annotations.Test
import de.flower.rmt.model.Team
import org.springframework.beans.factory.annotation.Autowired
import org.testng.Assert._
import javax.validation.{ConstraintViolation, Validator, ConstraintViolationException}
import scala.collection.JavaConversions._

/**
 *
 * @author oblume
 */

class TeamManagerTest extends AbstractIntegrationTests {

    @Autowired
    var validator: Validator = _

    @Test
    def testSave() {
        var entity = new Team()
        entity setName "Foo Bar"
        entity setClub club
        teamManager.save(entity)

        var id = entity.getId()
        entity = teamRepo.findOne(id)
        log info "" + entity

        var status = transactionManager.getTransaction(null)
        entity = teamRepo.findOne(id)
        teamRepo delete entity
        transactionManager.commit(status)

        entity = teamRepo.findOne(id)
        assert(entity == null)


    }

    @Test
    def testValidation() {
        var entity = new Team()
        entity setClub club

        intercept[ConstraintViolationException] {
            teamManager save entity
        }

        entity setName "     "
        var violations = validator.validate(entity)
        assertEquals(1, violations.size())

        var violation: ConstraintViolation[Team] = violations.toList.head
        assertEquals(entity.getName(), violation.getInvalidValue())
        log info "" + violation.getConstraintDescriptor


    }

}
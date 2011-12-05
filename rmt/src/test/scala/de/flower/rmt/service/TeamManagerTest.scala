package de.flower.rmt.service

import org.testng.annotations.Test
import de.flower.rmt.model.Team
import org.springframework.beans.factory.annotation.Autowired
import org.testng.Assert._
import javax.validation.{ConstraintViolation, Validator, ConstraintViolationException}
import scala.collection.JavaConversions._
import de.flower.rmt.test.AbstractIntegrationTests
import org.scalatest.Assertions

/**
 *
 * @author flowerrrr
 */

class TeamManagerTest extends AbstractIntegrationTests with Assertions {

    @Autowired
    var validator: Validator = _

    @Test
    def testSave() {
        var entity = new Team(testData.getClub())
        entity setName "Foo Bar"
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
        var entity = new Team(testData.getClub())

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
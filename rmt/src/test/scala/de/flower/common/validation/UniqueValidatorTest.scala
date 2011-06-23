package de.flower.common.validation

import org.springframework.beans.factory.annotation.Autowired
import de.flower.test.AbstractIntegrationTests
import org.testng.annotations.Test
import javax.validation.{ConstraintViolation, ConstraintViolationException, Validator}
import org.testng.Assert._
import scala.collection.JavaConversions._
import de.flower.rmt.model.{Users, Team}
import javax.persistence.Column
import org.mockito.Mockito._

/**
 *
 * @author oblume
 */

class UniqueValidatorTest extends AbstractIntegrationTests {

    @Autowired
    var validator: Validator = _


    @Test
    def testTableUnique() {

        // save one entity
        var teamName = "fc bayern"
        var entity = new Team(teamName, null, club)

        var violations = validator.validate(entity)
        assertEquals(0, violations.size())

        teamRepo save entity

        var id = entity.getId

        // try to save new team with same name
        entity = new Team(teamName, null, club)
        violations = validator.validate(entity)
        assertEquals(1, violations.size(), violations.toString())

        var violation: ConstraintViolation[Team] = violations.toList.head
        log info "" + violation.getConstraintDescriptor

        intercept[ConstraintViolationException] {
            teamManager save entity
        }

        // now edit the first team.
        entity = teamRepo.findOne(id)
        violations = validator.validate(entity)
        assertEquals(0, violations.size())

        teamRepo save entity
    }

}

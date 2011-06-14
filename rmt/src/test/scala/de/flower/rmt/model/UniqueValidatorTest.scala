package de.flower.rmt.model

import org.springframework.beans.factory.annotation.Autowired
import de.flower.test.AbstractIntegrationTests
import org.testng.annotations.Test
import javax.validation.{ConstraintViolation, ConstraintViolationException, Validator}
import org.testng.Assert._
import scala.collection.JavaConversions._

/**
 *
 * @author oblume
 */

class UniqueValidatorTest extends AbstractIntegrationTests {

    @Autowired
    var validator: Validator = _


    @Test
    def testValidation() {

        // save one entity
        var entity = new Team("1", null, club)

        var violations = validator.validate(entity)
        assertEquals(0, violations.size())

        teamRepo save entity

        // try to save team with same name
        entity = new Team("1", null, club)
        violations = validator.validate(entity)
        assertEquals(1, violations.size())

        var violation: ConstraintViolation[Team] = violations.toList.head
        log info "" + violation.getConstraintDescriptor

        intercept[ConstraintViolationException] {
            myTeamManager save entity
        }
    }
}

package de.flower.rmt.service;

import de.flower.common.test.Violations;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @author flowerrrr
 */

public class CalendarManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testValidationEndBeforeStart() {
        CalItemDto calItemDto = new CalItemDto();
        DateTime dt = new DateTime();
        calItemDto.setStartDateTime(dt);
        calItemDto.setEndDateTime(dt.minusMinutes(1));
        Set<ConstraintViolation<CalItemDto>> violations = validator.validate(calItemDto);
        log.info(Violations.dump(violations));
        Violations.assertViolation("{validation.calitem.endbeforestart}", violations);
    }

}
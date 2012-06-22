package de.flower.rmt.service;

import de.flower.common.test.Violations;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.type.CalendarType;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

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

    @Test
    public void testFindAllByUserAndRange() {
        CalItemDto calItemDto = new CalItemDto();
        calItemDto.setType(CalItem.Type.HOLIDAY);
        DateTime startDate = new DateTime();
        DateTime endDate = startDate;
        calItemDto.setStartDateTime(startDate);
        calItemDto.setEndDateTime(endDate);
        List<CalendarType> types = Arrays.asList(CalendarType.USER);
        // verify database is empty.
        assertTrue(calendarManager.findAllByCalendarAndRange(types, new DateTime(0), new DateTime(Long.MAX_VALUE)).isEmpty());
        calendarManager.save(calItemDto, securityService.getUser());

        assertEquals(calendarManager.findAllByCalendarAndRange(types, startDate, endDate).size(), 1);
        assertEquals(calendarManager.findAllByCalendarAndRange(types, startDate, endDate.plusDays(1)).size(), 1);
        assertEquals(calendarManager.findAllByCalendarAndRange(types, startDate.minusDays(1), endDate).size(), 1);
        assertEquals(calendarManager.findAllByCalendarAndRange(types, startDate.minusDays(1), endDate.plusDays(1)).size(), 1);

        assertEquals(calendarManager.findAllByCalendarAndRange(types, startDate.plusMillis(1), endDate).size(), 0);

        // let event span several months
        calItemDto.setStartDateTime(startDate.minusYears(1));
        calItemDto.setEndDateTime(endDate.plusYears(1));
        calendarManager.save(calItemDto, securityService.getUser());

        assertEquals(calendarManager.findAllByCalendarAndRange(types, startDate.minusDays(1), endDate).size(), 1);
        assertEquals(calendarManager.findAllByCalendarAndRange(types, startDate.minusYears(2), endDate).size(), 1);
    }

    @Test
    public void testFindAllByOtherAndRange() {
        DateTime startDate = new DateTime();
        DateTime endDate = startDate;
        // simply verify that method executes without exception.
        List<CalendarType> types = Arrays.asList(CalendarType.OTHERS);
        calendarManager.findAllByCalendarAndRange(types, startDate, endDate);
    }

    @Test
    public void testFindAllByCalenderAndRange() {
        DateTime startDate = new DateTime();
        DateTime endDate = startDate;
        calendarManager.findAllByCalendarAndRange(Arrays.asList(CalendarType.values()), startDate, endDate);
    }
}
package de.flower.rmt.service;

import de.flower.common.test.Violations;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.type.CalendarFilter;
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
        List<CalendarFilter> filters = Arrays.asList(CalendarFilter.USER);
        // verify database is empty.
        assertTrue(calendarManager.findAllByCalendarAndRange(filters, new DateTime(0), new DateTime(Long.MAX_VALUE)).isEmpty());
        calendarManager.save(calItemDto, securityService.getUser());

        assertEquals(calendarManager.findAllByCalendarAndRange(filters, startDate, endDate).size(), 1);
        assertEquals(calendarManager.findAllByCalendarAndRange(filters, startDate, endDate.plusDays(1)).size(), 1);
        assertEquals(calendarManager.findAllByCalendarAndRange(filters, startDate.minusDays(1), endDate).size(), 1);
        assertEquals(calendarManager.findAllByCalendarAndRange(filters, startDate.minusDays(1), endDate.plusDays(1)).size(), 1);

        assertEquals(calendarManager.findAllByCalendarAndRange(filters, startDate.plusMillis(1), endDate).size(), 0);

        // let event span several months
        calItemDto.setStartDateTime(startDate.minusYears(1));
        calItemDto.setEndDateTime(endDate.plusYears(1));
        calendarManager.save(calItemDto, securityService.getUser());

        assertEquals(calendarManager.findAllByCalendarAndRange(filters, startDate.minusDays(1), endDate).size(), 1);
        assertEquals(calendarManager.findAllByCalendarAndRange(filters, startDate.minusYears(2), endDate).size(), 1);
    }

    @Test
    public void testFindAllByOtherAndRange() {
        DateTime startDate = new DateTime();
        DateTime endDate = startDate;
        // simply verify that method executes without exception.
        List<CalendarFilter> filters = Arrays.asList(CalendarFilter.OTHERS);
        calendarManager.findAllByCalendarAndRange(filters, startDate, endDate);
    }

    @Test
    public void testFindAllByTeamAndRange() {
        Team team = testData.createTeamWithPlayers("FC Foobar", 20);

        DateTime startDate = new DateTime();
        DateTime endDate = startDate;
        // simply verify that method executes without exception.
        List<CalendarFilter> filters = Arrays.asList(new CalendarFilter(CalendarFilter.Type.TEAM, team));
        calendarManager.findAllByCalendarAndRange(filters, startDate, endDate);
    }

    @Test
    public void testFindAllByCalenderAndRange() {
        DateTime startDate = new DateTime();
        DateTime endDate = startDate;
        calendarManager.findAllByCalendarAndRange(calendarManager.getCalendarFilters(), startDate, endDate);
    }
}
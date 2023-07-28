package de.flower.rmt.ui.markup.html.form;

import org.joda.time.LocalTime;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;


public class TimeDropDownChoiceTest {

    @Test
    public void testClosestMatch() {
        List<LocalTime> choices = TimeDropDownChoice.getTimeChoices();
        int closestMatch = TimeDropDownChoice.closestMatch(new LocalTime(23, 44, 59, 000), choices);
        assertEquals(closestMatch, choices.size() - 1);
        closestMatch = TimeDropDownChoice.closestMatch(new LocalTime(23, 45, 59, 000), choices);
        assertEquals(closestMatch, choices.size() - 1);
        closestMatch = TimeDropDownChoice.closestMatch(new LocalTime(23, 59, 59, 000), choices);
        assertEquals(closestMatch, choices.size() - 1);
    }

}

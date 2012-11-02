package de.flower.rmt.ui.markup.html.weather;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class WeatherPanelTest {

    @Test
    public void testGetZipCode() {
        assertEquals(WeatherPanel.getZipCode(null), null);
        assertEquals(WeatherPanel.getZipCode("Address ohne PLZ "), null);
        assertEquals(WeatherPanel.getZipCode("Heisenberg-Allee 11\n80331 München"), "80331");
    }
}

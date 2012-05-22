package de.flower.rmt.service;


import de.flower.rmt.test.AbstractRMTIntegrationTests
import org.testng.annotations.Test
import static org.testng.Assert.assertEquals

/**
 * @author flowerrrr
 */
public class ClubManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testFindAllClubs() {
        def clubs = clubManager.findAllClubs();
        assertEquals(clubs.size(), 2)
    }
}
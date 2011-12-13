package de.flower.rmt.service;

import de.flower.rmt.model.User;
import de.flower.rmt.model.User_;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class RoleManagerTest extends AbstractIntegrationTests {

    @Test
    public void testRoles() {
        User user = testData.createUser();
        // get fresh copy
        user = userManager.loadById(user.getId(), User_.roles);

        userManager.save(user, true);
        assertFalse(user.isManager());
        // need to reload user to fresh roles collection
        user = userManager.loadById(user.getId(), User_.roles);
        assertTrue(user.isManager());

        userManager.save(user, false);
        assertTrue(user.isManager());
        user = userManager.loadById(user.getId(), User_.roles);
        assertFalse(user.isManager());
    }
}

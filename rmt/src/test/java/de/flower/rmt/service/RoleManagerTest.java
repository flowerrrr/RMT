package de.flower.rmt.service;

import de.flower.rmt.model.User;
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
        user = userManager.loadById(user.getId());

        userManager.save(user, true);
        assertTrue(roleManager.isManager(user));

        userManager.save(user, false);
        assertFalse(roleManager.isManager(user));
    }
}

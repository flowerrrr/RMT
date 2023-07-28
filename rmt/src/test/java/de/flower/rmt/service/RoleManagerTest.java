package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.User_;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class RoleManagerTest extends AbstractRMTIntegrationTests {

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

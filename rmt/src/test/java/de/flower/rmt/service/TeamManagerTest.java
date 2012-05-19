package de.flower.rmt.service;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.test.AbstractRMTIntegrationTests;
import org.springframework.transaction.TransactionStatus;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class TeamManagerTest extends AbstractRMTIntegrationTests {

    @Test
    public void testSave() {
        Team entity = new Team(testData.getClub());
        entity.setName("Foo Bar");
        teamManager.save(entity);

        Long id = entity.getId();
        entity = teamRepo.findOne(id);
        log.info("" + entity);

        TransactionStatus status = transactionManager.getTransaction(null);
        entity = teamRepo.findOne(id);
        teamRepo.delete(entity);
        transactionManager.commit(status);

        entity = teamRepo.findOne(id);
        assertTrue(entity == null);
    }

    @Test
    public void testValidation() {
        Team entity = new Team(testData.getClub());

        try {
            teamManager.save(entity);
            fail("Expected exception was not thrown");
        } catch (ConstraintViolationException e) {

        }

        entity.setName("     ");
        Set<ConstraintViolation<Team>> violations = validator.validate(entity);
        assertEquals(1, violations.size());

        ConstraintViolation<Team> violation = new ArrayList<ConstraintViolation<Team>>(violations).get(0);
        assertEquals(entity.getName(), violation.getInvalidValue());
        log.info("" + violation.getConstraintDescriptor());
    }

    @Test
    public void testDelete() {
        List<Team> teams = teamManager.findAll();
        for (Team team : teams) {
            teamManager.delete(team.getId());
            Team deletedTeam = teamManager.loadById(team.getId());
            assertTrue(deletedTeam.isDeleted());
        }
        assertTrue(teamManager.findAll().isEmpty());
    }
}
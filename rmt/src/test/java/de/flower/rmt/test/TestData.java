package de.flower.rmt.test;

import de.flower.rmt.model.*;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.repository.IClubRepo;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.repository.IPlayerRepo;
import de.flower.rmt.repository.ITeamRepo;
import de.flower.rmt.service.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */

@Service
public class TestData {

    @Autowired
    private ITeamRepo teamRepo;

    @Autowired
    private IClubRepo clubRepo;

    @Autowired
    private IEventRepo eventRepo;

    @Autowired
    private IPlayerRepo playerRepo;

    @Autowired
    private ITeamManager teamManager;

    @Autowired
    private IEventManager eventManager;

    @Autowired
    private IResponseManager responseManager;

    @Autowired
    private IVenueManager venueManager;

    @Autowired
    private IUserManager userManager;

    public void checkDataConsistency(EntityManager em) {
        // check that response->event->team matches response->player->team
        Query query = em.createQuery("from Response r  where r.event.team != r.player.team");
        List list = query.getResultList();
        Assert.assertTrue(list.isEmpty(), list.toString());
    }

    public Club getClub() {
        // load some often used entities
        return Validate.notNull(clubRepo.findOne(1L));
    }

    public static Club newClub() {
        Club club = new Club("Foo FC");
        return club;
    }

    public Team getJuveAmateure() {
        return Validate.notNull(teamRepo.findOne(1L));
    }

    public Team getOrCreateTeamWithPlayer(String name) {
        Team team = teamRepo.findByNameAndClub(name, getClub());
        if (team == null) {
            team = createTeam(name);
            List<User> users = createUsers(20);
            teamManager.addPlayers(team, users);
            // add user of security context to team
            teamManager.addPlayer(team, getTestUser());
        }
        return team;
    }

    public Team createTeam(String name) {
        Team team = teamManager.newInstance();
        team.setName(name);
        teamManager.save(team);
        return team;
    }

    public static Team newTeam() {
        return new Team(newClub());
    }


    public static User newUser() {
        User user = new User(newClub());
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@acme.com");
        user.setFullname(RandomStringUtils.randomAlphabetic(10));
        user.setInitialPassword("1234");
        user.setEncryptedPassword("io8ujalöjdfkalsöj");
        Role role = new Role(Role.Roles.MANAGER.getRoleName());
        user.getRoles().add(role);
        role.setUser(user);
        return user;
    }

    public User createUser() {
        return createUsers(1).get(0);
    }

    public List<User> createUsers(int count) {
        List<User> users=new ArrayList<User>();
        for (int i = 0; i < count; i++) {
            User user = userManager.newInstance();
            user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@acme.com");
            user.setFullname(RandomStringUtils.randomAlphabetic(10));
            userManager.save(user);
            users.add(user);
        }
        return users;
    }

    public static Event newEvent() {
        return new Event(newTeam());
    }

    public Event createEvent() {
        return createEventForTeam("Socca Five 2");
    }

    public Event createEventForTeam(String teamName) {
        Event event = eventManager.newInstance(EventType.Training);
        event.setDate(new Date());
        event.setTime(LocalTime.now());
        event.setSummary("Summary");
        event.setTeam(getOrCreateTeamWithPlayer(teamName));
        event.setVenue(venueManager.loadById(1L));
        eventManager.save(event);
        return event;
    }

    /**
     * Creates an event with some responses.
     */
    public Event createEventWithResponses() {
        Event event = createEvent();
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(15), RSVPStatus.ACCEPTED, "some comment");
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(1), RSVPStatus.DECLINED, "some comment");
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(2), RSVPStatus.DECLINED, "some comment");
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(3), RSVPStatus.ACCEPTED, "lore ipsum comment");
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(4), RSVPStatus.UNSURE, "some comment");
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(5), RSVPStatus.UNSURE, "some comment");
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(10), RSVPStatus.ACCEPTED, "gfrei mi");
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(11), RSVPStatus.ACCEPTED, "some comment");
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(12), RSVPStatus.ACCEPTED, "some comment");
        return event;
    }

    public User getTestUser() {
        return userManager.findByUsername(AbstractIntegrationTests.testUserName);
    }

    public Response createResponse() {
        Event event = createEvent();
        return new Response(event, playerRepo.findByTeam(event.getTeam()).get(1));
    }

}
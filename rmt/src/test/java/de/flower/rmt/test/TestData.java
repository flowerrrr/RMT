package de.flower.rmt.test;

import de.flower.common.util.geo.LatLng;
import de.flower.rmt.model.db.entity.*;
import de.flower.rmt.model.db.entity.event.AbstractSoccerEvent;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.entity.event.Event_;
import de.flower.rmt.model.db.entity.event.Match;
import de.flower.rmt.model.db.type.EventType;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.repository.*;
import de.flower.rmt.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Provides test data.
 * <p/>
 * Use #newXXX methods in unit tests.
 * Use #getXXX methods when running integration tests with underlying database.
 *
 * @author flowerrrr
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TestData {

    private final static Logger log = LoggerFactory.getLogger(TestData.class);

    @Autowired
    private ITeamRepo teamRepo;

    @Autowired
    private IClubRepo clubRepo;

    @Autowired
    private IEventRepo eventRepo;

    @Autowired
    private IPlayerRepo playerRepo;

    @Autowired
    private IActivityRepo activityRepo;

    @Autowired
    private ITeamManager teamManager;

    @Autowired
    private IUniformManager uniformManager;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IEventManager eventManager;

    @Autowired
    protected IInvitationManager invitationManager;

    @Autowired
    protected ICommentManager commentManager;

    @Autowired
    private IVenueManager venueManager;

    @Autowired
    private IOpponentManager opponentManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IActivityManager activityManager;

    private Random random = new Random();

    /**
     * Determines type of created test event.
     */
    private EventType eventType = EventType.Training;

    public TestData() {
        // interesting, constructor called twice.
        // see http://forum.springsource.org/showthread.php?9814-Constructor-called-twice-when-using-CGLIB-proxy-beans
        // log.info("TestData.<init>");
    }

    public void setEventType(final EventType eventType) {
        this.eventType = eventType;
    }

    public void checkDataConsistency(EntityManager em) {
/*
        // check that invitation->event->team matches invittee->user->team
        Query query = em.createQuery("from Invitation r  where r.event.team != r.player.team");
        List list = query.getResultList();
        Assert.assertTrue(list.isEmpty(), list.toString());
*/
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

    public Team createTeam(String name) {
        Team team = teamManager.newInstance();
        team.setName(name);
        teamManager.save(team);
        return team;
    }

    public Team createTeamWithPlayers(String name, int numPlayers) {
        Team team = createTeam(name);
        List<User> users = createUsers(numPlayers - 1);
        playerManager.addPlayers(team, users);
        // add user of security context to team
        playerManager.addPlayer(team, getTestUser());
        return team;
    }

    public Team newTeamWithPlayers(int numPlayers) {
        Team team = newTeam();
        List<User> users = newUsers(numPlayers);
        for (User user : users) {
            addPlayer(team, user);
        }
        return team;
    }

    public Team newTeam() {
        Team team = new Team(newClub());
        team.setName("Amateure");
        return team;
    }

    public User newUser() {
        User user = new User(newClub());
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@acme.com");
        user.setPhoneNumber("+49-189-8234238");
        user.setFullname(RandomStringUtils.randomAlphabetic(10));
        user.setInitialPassword("1234");
        user.setEncryptedPassword("io8ujalöjdfkalsöj");
        user.setStatus(random.nextBoolean() ? User.Status.FIT : User.Status.INJURED);
        Role role = new Role(Role.Roles.PLAYER.getRoleName());
        user.getRoles().add(role);
        role.setUser(user);
        // some get manager role
        if (random.nextBoolean()) {
            role = new Role(Role.Roles.MANAGER.getRoleName());
            user.getRoles().add(role);
            role.setUser(user);
        }
        return user;
    }

    public User newUserWithTeams() {
        Club club = newClub();
        Team team1 = new Team(club);
        Team team2 = new Team(club);
        Team team3 = new Team(club);
        User user = newUser();
        // add user to teams
        addPlayer(team1, user);
        addPlayer(team2, user);
        addPlayer(team3, user);
        return user;
    }

    private Player addPlayer(final Team team, final User user) {
        Player player = new Player(team, user);
        player.setRetired(false);
        player.setOptional(false);
        player.setNotification(true);
        team.getPlayers().add(player);
        user.getPlayers().add(player);
        return player;
    }

    public User createUser() {
        return createUsers(1).get(0);
    }

    public List<User> createUsers(int count) {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < count; i++) {
            User user = userManager.newInstance();
            user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@mailinator.com");
            user.setFullname(RandomStringUtils.randomAlphabetic(10));
            userManager.save(user);
            users.add(user);
        }
        return users;
    }

    public List<User> newUsers(int count) {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < count; i++) {
            User user = newUser();
            users.add(user);
        }
        return users;
    }

    public Event newEvent() {
        Team team = newTeamWithPlayers(15);
        Event event = eventType.newInstance(team.getClub());
        event.setTeam(team);
        // pick first player as manager who created this event.
        event.setCreatedBy(team.getPlayers().get(0).getUser());
        event.setDateTime(new DateTime());
        if (EventType.isSoccerEvent(event)) {
            ((AbstractSoccerEvent) event).setKickoff(new LocalTime());
            ((AbstractSoccerEvent) event).setUniform(newUniformList(event.getTeam()).get(0));
        }
        event.setVenue(newVenue(event.getTeam().getClub()));
        event.setSummary("2. Training");
        event.setComment("Kommt ja nicht zu spät!");
        for (Player player : event.getTeam().getPlayers()) {
            // create invitations
            Invitation invitation = new Invitation(event, player.getUser());
            event.getInvitations().add(invitation);
        }
        return event;
    }

    public Event createEvent(Team team, boolean createInvitations) {
        Event event = eventManager.newInstance(eventType);
        // use some time in the future
        event.setDateTime(new DateTime().plusHours(RandomUtils.nextInt(1000)));
        event.setSummary("Summary");
        event.setTeam(team);
        event.setVenue(venueManager.loadById(1L));
        if (EventType.isSoccerEvent(event)) {
            ((AbstractSoccerEvent) event).setKickoff(LocalTime.now());
            ((AbstractSoccerEvent) event).setUniform(createUniformList(team).get(0));
        }
        if (EventType.isMatch(event)) {
            ((Match) event).setOpponent(opponentManager.loadById(1L));
        }
        eventManager.create(event, createInvitations);
        return eventManager.loadById(event.getId(), Event_.team/*, Event_.invitations - does not work because of subclassing issues */);
    }

    public Event createEvent() {
        Team team = createTeamWithPlayers("FCB " + System.currentTimeMillis(), 15);
        Event event = createEvent(team, true);
        return event;
    }

    public List<Event> createEventsWithInvitations(final Team team, final int number, boolean past) {
        List<Event> list = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Event event = createEvent(team, true);
            // events are in the future by default
            if (past) {
                event.setDateTime(new DateTime().minusHours(RandomUtils.nextInt(1000)));
            }
            eventManager.save(event);
            list.add(event);
        }
        return list;
    }

    /**
     * Creates an event with some responses.
     */
    public Event createEventWithResponses() {
        Event event = createEvent();
        List<Invitation> invitations = invitationManager.findAllByEvent(event);
        respond(invitations.get(0), RSVPStatus.ACCEPTED, "some comment");
        respond(invitations.get(2), RSVPStatus.DECLINED, "some comment");
        respond(invitations.get(3), RSVPStatus.ACCEPTED, "some comment");
        respond(invitations.get(5), RSVPStatus.UNSURE, "some comment");
        respond(invitations.get(7), RSVPStatus.ACCEPTED, "some comment");
        respond(invitations.get(8), RSVPStatus.DECLINED, "some comment");
        respond(invitations.get(9), RSVPStatus.DECLINED, "some comment");
        respond(invitations.get(11), RSVPStatus.ACCEPTED, "some comment");
        respond(invitations.get(12), RSVPStatus.ACCEPTED, "some comment");
        // test multi-comment comments
        Comment comment = new Comment("some other comment", invitations.get(0), invitations.get(1).getUser());
        commentManager.save(comment);
        comment = new Comment("manager has to say something", invitations.get(0), event.getCreatedBy());
        commentManager.save(comment);
        // init collections
        event.getTeam().getPlayers().size();
        event.getTeam().getPlayers().get(0).getUser();
        return event;
    }

    private void respond(Invitation invitation, RSVPStatus status, String comment) {
        invitation.setStatus(status);
        invitationManager.save(invitation, comment);
    }

    public User getTestUser() {
        return userManager.findByUsername(AbstractRMTIntegrationTests.testUserName);
    }

    public Venue newVenue(Club club) {
        Venue venue = new Venue(club);
        venue.setName("Böglwiese");
        venue.setAddress("Putzbrunner Str. 13\n81739 München");
        venue.setLatLng(new LatLng(48.12123, 11.01231231));
        return venue;
    }

    public List<Uniform> createUniformList(Team team) {
        List<Uniform> uniforms = newUniformList(team);
        for (Uniform uniform : uniforms) {
            uniformManager.save(uniform);
        }
        return uniformManager.findAllByTeam(team);
    }

    public List<Uniform> newUniformList(Team team) {
        Uniform j1 = new Uniform(team);
        j1.setName("Trikotsatz 1");
        j1.setShirt("white/red stripes");
        j1.setShorts("red");
        j1.setSocks("white");
        Uniform j2 = new Uniform(team);
        j2.setName("Trikotsatz 2");
        j2.setShirt("black");
        j2.setShorts("red");
        j2.setSocks("gold");
        return Arrays.asList(j1, j2);
    }

    public List<Activity> newActivities(final int num) {
        Club club = newClub();
        List<Activity> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Activity entity = new Activity(club);
            entity.setDate(new DateTime().minusMillis(RandomUtils.nextInt((int) TimeUnit.DAYS.toMillis(10))).toDate());
            entity.setMessage("Some message " + RandomStringUtils.randomAscii(20));
            list.add(entity);
        }
        // sort desc by date
        Collections.sort(list, new Comparator<Activity>() {
            @Override
            public int compare(final Activity o1, final Activity o2) {
                return (int) (o2.getDate().getTime() - o1.getDate().getTime());
            }
        });
        return list;
    }

    public void createActivities(final int num) {
        Club club = getClub();
        for (int i = 0; i < num; i++) {
            Activity entity = new Activity(club);
            entity.setDate(new DateTime().minusMillis(RandomUtils.nextInt((int) TimeUnit.DAYS.toMillis(10))).toDate());
            entity.setMessage("Some message " + RandomStringUtils.randomAscii(20));
            activityRepo.save(entity);
        }
    }

    public CalItemDto newCalItemDto() {
        CalItem entity = new CalItem();
        entity.setStartDateTime(new DateTime().withTime(2, 15, 0, 0));
        entity.setEndDateTime(new DateTime().withTime(22, 30, 0, 0));
        entity.setSummary("summary");
        entity.setType(CalItem.Type.OTHER);
        entity.setUser(new User(new Club("new club")));
        return CalItemDto.fromEntity(entity);
    }


    /**
     * Method replaces dbUnit xml file. Seems to have more advantages to use programmatic approach.
     */
//    public void insertTestData() {
//        Club juve = createClub("Juventus Urin", 48.13724243994332, 11.575392225925508);
//        Club fcb = createClub("FC Bayern", 48.13724243994332, 11.575392225925508);
//
//        createProperty(juve, "uservoice.token", "mNTsoCUuSEaF5qjGfDGIuQ");
//
//        User user1 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "admin-rmt@mailinator.com", "Flowerrr", juve);
//        User user2 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "manager-rmt@mailinator.com", "Sepp", juve);
//        User user3 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player-rmt@mailinator.com", "Ingo", juve);
//        User user4 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "both-rmt@mailinator.com", "Maik", juve);
//        User user5 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "bayern-rmt@mailinator.com", "Uli Hoeneß", juve);
//        User user6 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player1-rmt@mailinator.com", "Hansi", juve);
//        User user7 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player2-rmt@mailinator.com", "Horst", juve);
//        User user8 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player3-rmt@mailinator.com", "Thorsten", juve);
//        User user9 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player4-rmt@mailinator.com", "Michi", juve);
//        User user10 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player5-rmt@mailinator.com", "Paul", juve);
//        User user11 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player6-rmt@mailinator.com", "Hugo", juve);
//        User user12 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player7-rmt@mailinator.com", "Alder", juve);
//        User user13 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player8-rmt@mailinator.com", "Dragan", juve);
//        User user14 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player9-rmt@mailinator.com", "Stefan", juve);
//        User user15 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player10-rmt@mailinator.com", "Erkan", juve);
//        User user16 = createUser("81dc9bdb52d04dc20036dbd8313ed055", "1234", "player11-rmt@mailinator.com", "Süpür", juve);
//    }
}
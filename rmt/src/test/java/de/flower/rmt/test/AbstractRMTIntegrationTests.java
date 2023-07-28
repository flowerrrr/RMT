package de.flower.rmt.test;

/**
 * @author flowerrrr
 */

import de.flower.common.service.security.PasswordGenerator;
import de.flower.common.test.mock.LogBackListAppender;
import de.flower.rmt.repository.IClubRepo;
import de.flower.rmt.repository.IEventRepo;
import de.flower.rmt.repository.IInvitationRepo;
import de.flower.rmt.repository.ITeamRepo;
import de.flower.rmt.repository.IUserRepo;
import de.flower.rmt.security.SecurityService;
import de.flower.rmt.service.ActivityManager;
import de.flower.rmt.service.ApplicationService;
import de.flower.rmt.service.BlogManager;
import de.flower.rmt.service.CalendarManager;
import de.flower.rmt.service.ClubManager;
import de.flower.rmt.service.CommentManager;
import de.flower.rmt.service.EventManager;
import de.flower.rmt.service.EventTeamManager;
import de.flower.rmt.service.IUrlProvider;
import de.flower.rmt.service.InvitationManager;
import de.flower.rmt.service.LineupManager;
import de.flower.rmt.service.PlayerManager;
import de.flower.rmt.service.ResponseManager;
import de.flower.rmt.service.RoleManager;
import de.flower.rmt.service.TeamManager;
import de.flower.rmt.service.UniformManager;
import de.flower.rmt.service.UserManager;
import de.flower.rmt.service.geocoding.GoogleGeocodingService;
import de.flower.rmt.service.mail.MailService;
import de.flower.rmt.service.mail.NotificationService;
import de.flower.rmt.service.mail.TemplateService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for integration tests from app-layer downwards. no ui-support.
 * Use @link AbstractWicketIntegrationTests.
 *
 * @author flowerrrr
 */
@SuppressWarnings("Dependency")
@Listeners({de.flower.common.test.ExceptionLoggerTestListener.class})
@ContextConfiguration(classes = {AbstractRMTIntegrationTestsConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AbstractRMTIntegrationTests extends AbstractTestNGSpringContextTests {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**************************************************************************/
    /*                        Daos                                            */

    /**
     * **********************************************************************
     */

    @Autowired
    protected ITeamRepo teamRepo;

    @Autowired
    protected IClubRepo clubRepo;

    @Autowired
    protected IUserRepo userRepo;

    @Autowired
    protected IEventRepo eventRepo;

    @Autowired
    protected IInvitationRepo invitationRepo;

    /**************************************************************************/
    /*                        Services                                        */

    /**
     * **********************************************************************
     */

    @Autowired
    protected TeamManager teamManager;

    @Autowired
    protected UserManager userManager;

    @Autowired
    protected RoleManager roleManager;

    @Autowired
    protected EventManager eventManager;

    @Autowired
    protected PlayerManager playerManager;

    @Autowired
    protected ResponseManager responseManager;

    @Autowired
    protected InvitationManager invitationManager;

    @Autowired
    protected CommentManager commentManager;

    @Autowired
    protected LineupManager lineupManager;

    @Autowired
    protected CalendarManager calendarManager;

    @Autowired
    protected UniformManager uniformManager;

    @Autowired
    protected ActivityManager activityManager;

    @Autowired
    protected ClubManager clubManager;

    @Autowired
    protected BlogManager blogManager;

    @Autowired
    protected UserDetailsService userDetailService;

    @Autowired
    protected GoogleGeocodingService geocodingService;

    @Autowired
    protected MailService mailService;

    @Autowired
    protected TemplateService templateService;

    @Autowired
    protected NotificationService notificationService;

    @Autowired
    protected EventTeamManager eventTeamManager;

    @Autowired
    protected SecurityService securityService;

    @Autowired
    protected ApplicationService applicationService;

    @Autowired
    protected IUrlProvider urlProvider;

    @Autowired
    protected SecurityContextHolderStrategy securityContextHolderStrategy;

    @Autowired
    protected Validator validator;

    @Autowired
    protected PasswordGenerator passwordGenerator;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected PlatformTransactionManager transactionManager;

    @Autowired
    protected DataSource dataSource;

    protected LogBackListAppender listAppender;

    @Autowired
    protected TestData testData;

    private Database db;

    public static final String testUserName = "manager-rmt@mailinator.com";

    @BeforeClass
    protected final void initClass() {
        listAppender = LogBackListAppender.configureListAppender();
        // use db-unit to refresh the the test data
        Map<String, Object> properties = new HashMap();
        properties.put("http://www.dbunit.org/properties/datatypeFactory", new org.dbunit.ext.h2.H2DataTypeFactory());
        db = new Database(properties);
        db.setDataSource(dataSource);
    }

    @BeforeMethod
    protected final void initTest() throws Exception {
        try {
            // reset database
            resetTestdata();
            resetListAppender();

            initializeSecurityContextWithTestUser();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @AfterMethod
    protected final void afterTest() throws Exception {
        try {
            // dump database to disk
            // full database export (cannot export users table: always a dbunit error wiht this table.)
            String[] tables = new String[]{"club", "team", "venue", "player", "event", "invitation"};
            db.export("src/test/database/data/export.xml", tables);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Initialize security context with test user.
     */
    protected void initializeSecurityContextWithTestUser() {
        // set the security context with a test user.
        Validate.notNull(userRepo.findByEmail(testUserName), "Make sure test user is present in test database.");
        UserDetails principal = userDetailService.loadUserByUsername(testUserName);
        Authentication authentication = new TestingAuthenticationToken(principal, principal.getPassword(), new ArrayList(principal.getAuthorities()));
        securityContextHolderStrategy.getContext().setAuthentication(authentication);
    }

    /**
     * Reset testdata. Most workflow unit tests are @NotTransactional annotated to have a more realistic transactional behaviour of the workflow-methods. That means we have to reset any test-data that
     * was modified during a testmethod.
     */
    private void resetTestdata() {
        log.info("Resetting test data in database.");

        db.deleteAll(Database.createDataSet("/data/test_data_delete.xml"));

        db.cleanInsert(Database.createDataSet("/data/test_data.xml"));
        // testData.insertTestData();

    }

    /**
     * Reset the internal buffer of the mock appender before each test method is started.
     */
    private void resetListAppender() {
        if (listAppender != null) {
            listAppender.reset();
        }
    }

    /**
     * Prints out "test started" message.
     * Requires junit 4.8 test runner that is able to process @Rule annotations.
     * Does not work with eclipse 3.5, which has junit 4.5 bundled.
     */
    @BeforeMethod(alwaysRun = true)
    public void testStarted(Method method) {
        log.info("***************************************************************");
        log.info("Test [" + method.getName() + "] started.");
        log.info("***************************************************************");
    }

    /**
     * Prints out "test finished" message.
     */
    @AfterMethod
    public void testFinished(Method method) {
        log.info("***************************************************************");
        log.info("Test [" + method.getName() + "()] finshed.");
        log.info("***************************************************************");
    }
}

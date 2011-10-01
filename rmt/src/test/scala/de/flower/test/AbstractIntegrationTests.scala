package de.flower.test

import mock.{IListAppender, LogBackListAppender}
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import javax.persistence.{PersistenceContext, EntityManager}
import org.scalatest.Assertions
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource
import org.slf4j.{LoggerFactory, Logger}
import ch.qos.logback.classic.spi.ILoggingEvent
import de.flower.rmt.repository.{IUserRepo, IClubRepo, ITeamRepo}
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolderStrategy
import de.flower.rmt.service.geocoding.IGeocodingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.ArrayList
import org.apache.commons.lang3.Validate
import de.flower.rmt.service.{IEventManager, IUserManager, ITeamManager}
import org.testng.annotations.{AfterMethod, BeforeClass, Listeners, BeforeMethod}
import org.springframework.test.annotation.DirtiesContext

/**
 *
 * @author flowerrrr
 */
@Listeners(Array(classOf[de.flower.test.ExceptionLoggerTestListener]))
// in-memory database -> no need for transactional support and rollbacks
//@TestExecutionListeners(Array(classOf[TransactionalTestExecutionListener]))
// @Transactional
@ContextConfiguration(locations = Array("/applicationContext-test.xml"))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AbstractIntegrationTests extends AbstractTestNGSpringContextTests with Assertions with TestMethodListener {

    protected var log: Logger = LoggerFactory.getLogger(this.getClass());

    /**************************************************************************/
    /*                        Daos                                            */
    /**************************************************************************/

    @Autowired
    protected var teamRepo: ITeamRepo = _

    @Autowired
    protected var clubRepo: IClubRepo = _

    @Autowired
    protected var userRepo: IUserRepo = _

    /**************************************************************************/
    /*                        Services                                        */
    /**************************************************************************/

    @Autowired
    protected var teamManager: ITeamManager = _

    @Autowired
    protected var userManager: IUserManager = _

    @Autowired
    protected var eventManager: IEventManager = _

    @Autowired
    protected var userDetailService: UserDetailsService = _

    @Autowired
    protected var geocodingService: IGeocodingService = _

    @Autowired
    protected var securityContextHolderStrategy: SecurityContextHolderStrategy = _

    @PersistenceContext
    protected var em: EntityManager = _

    @Autowired
    protected var transactionManager: PlatformTransactionManager = _

    @Autowired
    protected var dataSource: DataSource = _

    protected var listAppender: IListAppender[ILoggingEvent] = _

    @Autowired
    protected var testData: TestData = _

    private var db: Database = _

    @BeforeClass
    def initClass() {
        listAppender = LogBackListAppender.configureListAppender()
        // use db-unit to refresh the the test data
        val properties = Map("http://www.dbunit.org/properties/datatypeFactory" -> new org.dbunit.ext.h2.H2DataTypeFactory())
        db = new Database(properties)
        db.setDataSource(dataSource)
    }

    @BeforeMethod
    def initTest() {
        try {
            // reset database
            resetTestdata()
            resetListAppender()

            initializeSecurityContextWithTestUser()
        } catch {
            case e: Exception => {
                log.error(e.getMessage(), e)
                throw e;
            }
        }
    }

    @AfterMethod
    def afterTest() {
        try {
            // dump database to disk
            // full database export (cannot export users table: always a dbunit error wiht this table.)
            val tables = Array("club", "team", "venue", "player", "event", "response", "invitation");
            db.export("src/test/database/data/export.xml", tables)
        } catch {
            case e: Exception => {
                log.error(e.getMessage(), e)
                throw e;
            }
        }
    }

    /**
     * Initialize security context with test user.
     */
    protected def initializeSecurityContextWithTestUser() {
        // set the security context with a test user.
        var username = "manager-rmt@mailinator.com"
        Validate.notNull(userRepo.findByEmail(username), "Make sure test user is present in test database.")
        val principal = userDetailService.loadUserByUsername(username)
        val authentication = new TestingAuthenticationToken(principal, principal.getPassword, new ArrayList(principal.getAuthorities))
        securityContextHolderStrategy.getContext.setAuthentication(authentication)
    }

    /**
     * Reset testdata. Most workflow unit tests are @NotTransactional annotated to have a more realistic transactional behaviour of the workflow-methods. That means we have to reset any test-data that
     * was modified during a testmethod.
     */
    def resetTestdata() {
        log.info("Resetting test data in database.")

        db.deleteAll(Database.createDataSet("/data/test_data_delete.xml"))
        db.cleanInsert(Database.createDataSet("/data/test_data.xml"))

        // checkDataConsistency();
    }

    /**
     * Reset the internal buffer of the mock appender before each test method is started.
     */
    def resetListAppender() {
        if (listAppender != null) {
            listAppender.reset();
        }
    }
}

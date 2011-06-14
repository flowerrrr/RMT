package de.flower.test

import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.context.{TestExecutionListeners, ContextConfiguration}
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.beans.factory.annotation.Autowired
import de.flower.rmt.service.ITeamManager
import javax.persistence.{TransactionRequiredException, PersistenceContext, EntityManager}
import org.scalatest.Assertions
import org.springframework.transaction.PlatformTransactionManager
import org.hibernate.AssertionFailure
import org.slf4j.{LoggerFactory, Logger}
import de.flower.rmt.model.Club
import de.flower.rmt.repository.{IClubRepo, ITeamRepo}
import com.google.common.base.Preconditions._
import javax.sql.DataSource
import java.lang.reflect.Method
import org.testng.annotations.{Listeners, BeforeMethod, AfterMethod}

/**
 *
 * @author oblume
 */
@Listeners(Array(classOf[de.flower.test.ExceptionLoggerTestListener]))
@TestExecutionListeners(Array(classOf[TransactionalTestExecutionListener]))
@Transactional
@ContextConfiguration(locations = Array("/applicationContext-test.xml"))
class AbstractIntegrationTests extends AbstractTestNGSpringContextTests with Assertions {

    protected var log: Logger = LoggerFactory.getLogger(this.getClass());


    /**************************************************************************/
    /*                        Daos                                            */
    /**************************************************************************/

    @Autowired
    protected var teamRepo: ITeamRepo = _

    @Autowired var clubRepo: IClubRepo = _


    /**************************************************************************/
    /*                        Services                                        */
    /**************************************************************************/

    @Autowired
    protected var myTeamManager: ITeamManager = _


    @PersistenceContext
    protected var em: EntityManager = _

    @Autowired
    protected var transactionManager: PlatformTransactionManager = _

    @Autowired
    protected var dataSource: DataSource = _


    /**Club used for testing. */
    protected var club: Club = _

    @BeforeMethod
    def init() {
        // reset database
        resetTestdata();

        // load some often used entities
        club = checkNotNull(clubRepo.findOne(1L))
    }

    /**
     * Reset testdata. Most workflow unit tests are @NotTransactional annotated to have a more realistic transactional behaviour of the workflow-methods. That means we have to reset any test-data that
     * was modified during a testmethod.
     */
    def resetTestdata() {
        log.info("Resetting test data in database.");
        // use db-unit to refresh the the test data
        var db: Database = new Database();
        db.setDataSource(dataSource);

        db.deleteAll(Database.createDataSet("/truncate_tables.xml"));
        db.cleanInsert(Database.createDataSet("/test_data.xml"));

        // checkDataConsistency();
    }


    /**
     * Sync hibernate with database. This is done inside the transaction shortly before commiting/rollbacking the transaction.
     *
     * @param method the method
     */
    @AfterMethod
    def syncDatabase(method: Method) {
        log info "Flushing EM"
        try {
            em.flush();
        } catch {
            case e: AssertionFailure => {
                log info "Test had previous hibernate errors. Cannot sync with database."
            }
            case e: TransactionRequiredException => {
                log info "Test " + method.getName + " is not transactional."
            }
            case e: RuntimeException => {
                var message: String = "Testmethod " + method.getName() +
                        " failed. Fix this testcase cause all following tests will be skipped after this error!";
                log.error(message, e);
                throw new RuntimeException(message, e);
            }
        }
    }

    /**
     * Test started.
     *
     * @param method the method
     */
    @BeforeMethod(alwaysRun = true)
    def testStarted(method: Method): Unit = {
        log.info("***************************************************************")
        log.info("Test [" + method.getName + "] started.")
        log.info("***************************************************************")
    }

    /**
     * Test finished.
     *
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    def testFinished(method: Method): Unit = {
        log.info("***************************************************************")
        log.info("Test [" + method.getName + "()] finshed.")
        log.info("***************************************************************")
    }
}
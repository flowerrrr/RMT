package de.flower.test

import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.context.{TestExecutionListeners, ContextConfiguration}
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.beans.factory.annotation.Autowired
import de.flower.rmt.dao.IMyTeamDao
import de.flower.rmt.service.IMyTeamManager
import org.testng.annotations.AfterMethod
import javax.persistence.{TransactionRequiredException, PersistenceContext, EntityManager}
import org.scalatest.Assertions
import javax.transaction.TransactionManager
import org.springframework.transaction.PlatformTransactionManager

/**
 *
 * @author oblume
 */
@TestExecutionListeners(Array(classOf[TransactionalTestExecutionListener]))
@Transactional
@ContextConfiguration(locations = Array("/applicationContext-test.xml"))
class AbstractIntegrationTests extends AbstractTestNGSpringContextTests with Assertions {

    /**************************************************************************/
    /*                        Daos                                            */
    /**************************************************************************/

    @Autowired
    protected var myTeamDao: IMyTeamDao = _


    /**************************************************************************/
    /*                        Services                                        */
    /**************************************************************************/

    @Autowired
    protected var myTeamManager: IMyTeamManager = _


    @PersistenceContext
    protected var em: EntityManager = _

    @Autowired
    protected var transactionManager: PlatformTransactionManager = _

    /**
     * Sync hibernate with database. This is done inside the transaction shortly before commiting/rollbacking the transaction.
     *
     * @param method the method
     */
    @AfterMethod
    def syncDatabase(method: java.lang.reflect.Method) {
        logger info "Flushing EM"
        try {
            em.flush();
        } catch {
            case e: TransactionRequiredException => {
                logger info "Test " + method.getName + " is not transactional."
            }
            case e: RuntimeException => {
                var message: String = "Testmethod " + method.getName() +
                        " failed. Fix this testcase cause all following tests will be skipped after this error!";
                logger.error(message, e);
                throw new RuntimeException(message, e);
            }
        }
    }


    /*
    @AfterMethod(alwaysRun = true)
    def syncDatabase(java.lang.reflect.Method method) {
        Session session;
        try {
            session = getHibernateSession();
        } catch (HibernateException e) {
            log.info("Unit test " + method.getName() + " is not transactional.");
            return;
        }
        if (session != null) {
            try {
                log.info("Flushing session!");
                session.flush();
            } catch (HibernateException e) {
                String message = "Testmethod " + method.getName()
                        + " failed. Fix this testcase cause all following tests will be skipped after this error!";
                log.error(message, e);
                throw new RuntimeException(message, e);
            }
        }
        disableHibernateDebugging();
    }
    */

}
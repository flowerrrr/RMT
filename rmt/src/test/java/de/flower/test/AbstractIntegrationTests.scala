package de.flower.test

import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.context.{TestExecutionListeners, ContextConfiguration}
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.beans.factory.annotation.Autowired
import de.flower.rmt.dao.IMyTeamDao
import de.flower.rmt.service.IMyTeamManager

/**
 *
 * @author oblume
 */
@TestExecutionListeners(Array(classOf[TransactionalTestExecutionListener]))
@Transactional
@ContextConfiguration(locations = Array("/applicationContext-test.xml"))
class AbstractIntegrationTests extends AbstractTestNGSpringContextTests {

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

}
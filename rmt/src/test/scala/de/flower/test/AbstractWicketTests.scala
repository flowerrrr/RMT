package de.flower.test

import org.apache.wicket.protocol.http.WebApplication
import org.springframework.context.ApplicationContext
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.mockito.Mockito._
import scala.collection.JavaConversions._
import org.apache.wicket.Component
import org.apache.wicket.util.tester.{FormTester, WicketTester, WicketTesterHelper}

/**
 * Base class for testing wicket components using TestNG.
 */
abstract class AbstractWicketTests extends AbstractIntegrationTests {

    @BeforeMethod def init: Unit = {
        createTester(applicationContext)
    }

    @AfterMethod def cleanup: Unit = {
        validateMockitoUsage
    }

    protected def createWebApp(ctx: ApplicationContext, flag: Boolean): WebApplication

    private def createTester(ctx: ApplicationContext): Unit = {
        val webApp: WebApplication = createWebApp(ctx, true)
        wicketTester = new RMTWicketTester(webApp)
    }



    protected var wicketTester: RMTWicketTester = null

}
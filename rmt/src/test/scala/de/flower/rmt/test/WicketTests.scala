package de.flower.rmt.test

import org.springframework.context.ApplicationContext
import org.apache.wicket.protocol.http.WebApplication
import de.flower.rmt.ui.app.TestApplication
import org.testng.annotations.{AfterMethod, BeforeMethod}
import org.mockito.Mockito._

/**
 * 
 * @author flowerrrr
 */

class WicketTests extends AbstractIntegrationTests {


    protected def createWebApp(ctx: ApplicationContext, flag: Boolean): WebApplication = {
        return new TestApplication(ctx);
    }

    @BeforeMethod def init: Unit = {
        createTester(applicationContext)
    }

    @AfterMethod def cleanup: Unit = {
        validateMockitoUsage
    }

    private def createTester(ctx: ApplicationContext): Unit = {
        val webApp: WebApplication = createWebApp(ctx, true)
        wicketTester = new RMTWicketTester(webApp)
    }



    protected var wicketTester: RMTWicketTester = null

}
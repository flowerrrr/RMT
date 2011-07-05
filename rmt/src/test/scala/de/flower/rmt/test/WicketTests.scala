package de.flower.rmt.test

import de.flower.test.AbstractWicketTests
import org.springframework.context.ApplicationContext
import org.apache.wicket.protocol.http.WebApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.authentication.TestingAuthenticationToken
import de.flower.rmt.model.Role
import de.flower.rmt.ui.app.TestApplication
import com.google.common.base.Preconditions._
import org.testng.annotations.{BeforeMethod, BeforeClass}

/**
 * 
 * @author oblume
 */

class WicketTests extends AbstractWicketTests {


    override protected def createWebApp(ctx: ApplicationContext, flag: Boolean): WebApplication = {
        return new TestApplication(ctx);
    }


}
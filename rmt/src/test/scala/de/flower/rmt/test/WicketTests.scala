package de.flower.rmt.test

import de.flower.test.AbstractWicketTests
import org.springframework.context.ApplicationContext
import org.apache.wicket.protocol.http.WebApplication
import de.flower.rmt.ui.app.TestApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.authentication.TestingAuthenticationToken
import de.flower.rmt.ui.common.page.login.Role
import org.testng.annotations.BeforeClass
import de.flower.rmt.ui.app.TestApplication

/**
 * 
 * @author oblume
 */

class WicketTests extends AbstractWicketTests {

    @Autowired
    protected var securityContextHolderStrategy: SecurityContextHolderStrategy = _


    protected def createWebApp(ctx: ApplicationContext, flag: Boolean): WebApplication = {
        return new TestApplication(ctx);
    }

	/**
	 * Initialize security context with test user.
	 */
    @BeforeClass
    protected def initializeSecurityContextWithTestUser() {
		// set the security context with a test user.
        var authentication = new TestingAuthenticationToken("manager", "manager", Role.MANAGER.getRoleName)
        securityContextHolderStrategy.getContext.setAuthentication(authentication)
	}


}
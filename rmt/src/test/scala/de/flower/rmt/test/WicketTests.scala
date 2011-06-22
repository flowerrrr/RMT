package de.flower.rmt.test

import de.flower.test.AbstractWicketTests
import org.springframework.context.ApplicationContext
import org.apache.wicket.protocol.http.WebApplication
import de.flower.rmt.ui.common.app.TestApplication

/**
 * 
 * @author oblume
 */

class WicketTests extends AbstractWicketTests {

    protected def createWebApp(ctx: ApplicationContext, flag: Boolean): WebApplication = {
        return new TestApplication(ctx);
    }
}
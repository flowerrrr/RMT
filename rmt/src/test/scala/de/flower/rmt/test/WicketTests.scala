package de.flower.rmt.test

import de.flower.test.WicketUnitTestBase
import org.springframework.context.ApplicationContext
import org.apache.wicket.protocol.http.WebApplication
import de.flower.rmt.ui.app.Application

/**
 * 
 * @author oblume
 */

class WicketTests extends WicketUnitTestBase {

    protected def createWebApp(ctx: ApplicationContext, flag: Boolean): WebApplication = {
        return new Application();
    }
}
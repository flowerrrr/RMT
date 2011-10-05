package de.flower.rmt.test

import de.flower.test.AbstractWicketTests
import org.springframework.context.ApplicationContext
import org.apache.wicket.protocol.http.WebApplication
import de.flower.rmt.ui.app.TestApplication
import org.apache.wicket.Component
import de.flower.common.ui.Css

/**
 * 
 * @author flowerrrr
 */

class WicketTests extends AbstractWicketTests {


    override protected def createWebApp(ctx: ApplicationContext, flag: Boolean): WebApplication = {
        return new TestApplication(ctx);
    }


}
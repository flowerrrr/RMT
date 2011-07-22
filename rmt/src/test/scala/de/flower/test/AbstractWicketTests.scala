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
        wicketTester = new WicketTester(webApp)
    }

    /**
     * Get the text document that was written as part of this response.
     *
     * @return html markup
     */
    protected def getPageDump: String = {
        return wicketTester.getLastResponse.getDocument
    }

    /**
     * Gets the debug component trees. Same as {@link WicketTester#debugComponentTrees()}, only does not log output,
     * rather returns it as a string.
     *
     * @return the debug component trees
     */
    protected def getDebugComponentTrees: String = {
        return getDebugComponentTrees("")
    }

    protected def getDebugComponentTrees(filter: String): String = {
        val s: StringBuilder = new StringBuilder
        for (obj <- WicketTesterHelper.getComponentData(wicketTester.getLastRenderedPage)) {
            if (obj.path.matches(".*" + filter + ".*")) {
                s.append("path\t").append(obj.path).append(" \t").append(obj.`type`).append(" \t[").append(obj.value).append("]\n")
            }
        }
        return s.toString
    }

    /**
     * Gets the first behavior of a component that matches a specified class.
     *
     * @param <T> the behavior class type
     * @param component the component
     * @param behaviourClass the behaviour class
     * @return the behavior
     * @throws IllegalArgumentException if no behavior of specified class is found.
     */
    protected def getBehavior[T](component: Component, behaviourClass: Class[T]): T = {
        for (iBehavior <- component.getBehaviors) {
            if (iBehavior.getClass == behaviourClass) {
                return iBehavior.asInstanceOf[T]
            }
        }
        throw new IllegalArgumentException("No behavior of class [" + behaviourClass + "] found in component [" + component + "].")
    }



    protected var wicketTester: WicketTester = null

    protected var formTester: FormTester = null
}
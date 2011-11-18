package de.flower.rmt.test

import org.apache.wicket.protocol.http.WebApplication
import scala.collection.JavaConversions._
import org.apache.wicket.Component
import org.apache.wicket.util.tester.{FormTester, WicketTester, WicketTesterHelper}
import scala.Boolean
import org.slf4j.{LoggerFactory, Logger}
import org.apache.wicket.protocol.http.mock.MockHttpServletRequest
import org.apache.wicket.request.IRequestHandler
import de.flower.common.ui.Css
import de.flower.common.ui.serialize.{Filter, LoggingSerializer}
import de.flower.rmt.model.RSVPStatus
import junit.framework.AssertionFailedError
import java.util.ArrayList
import org.apache.wicket.util.tester.WicketTesterHelper.ComponentData
import junit.framework.Assert._
import org.apache.wicket.util.lang.Classes

/**
 *
 * @author flowerrrr
 */

class RMTWicketTester(application: WebApplication) extends WicketTester(application) {

    private val log: Logger = LoggerFactory.getLogger(this.getClass());

    private val filter = new Filter("\"de\\.flower\\.rmt\\.model\\.[^-]*?\"")

    filter.addExclusion(classOf[RSVPStatus].getName())

    private val loggingSerializer = new LoggingSerializer(filter);

    override def processRequest(forcedRequest: MockHttpServletRequest, forcedRequestHandler: IRequestHandler, redirect: Boolean): Boolean = {
        val b = super.processRequest(forcedRequest, forcedRequestHandler, redirect);
        val page = getLastRenderedPage()
        if (page != null) {
            loggingSerializer.notify(page, null);
        }
        return b;
    }

    /**
     * Get the text document that was written as part of this response.
     *
     * @return html markup
     */
    def getPageDump: String = {
        return getLastResponse.getDocument
    }

    /**
     * Gets the debug component trees. Same as {@link WicketTester#debugComponentTrees()}, only does not log output,
     * rather returns it as a string.
     *
     * @return the debug component trees
     */
    def getDebugComponentTrees: String = {
        return getDebugComponentTrees("")
    }

    def getDebugComponentTrees(filter: String): String = {
        val s: StringBuilder = new StringBuilder
        for (obj <- WicketTesterHelper.getComponentData(getLastRenderedPage)) {
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
    def getBehavior[T](component: Component, behaviourClass: Class[T]): T = {
        for (iBehavior <- component.getBehaviors) {
            if (iBehavior.getClass == behaviourClass) {
                return iBehavior.asInstanceOf[T]
            }
        }
        throw new IllegalArgumentException("No behavior of class [" + behaviourClass + "] found in component [" + component + "].")
    }

    def assertValidation(field: Component, value: String, assertion: Boolean) {
        var formTester = getFormTester();
        formTester.setValue(field, value)
        executeAjaxEvent(field, "onblur")
        dumpPage()
        val cssClass = if (assertion) Css.VALID else Css.ERROR
        assertContains("class=\"" + cssClass)
    }

    private var formTester: FormTester = null

    def getFormTester(path: String = "form"): FormTester = {
        if (formTester == null) {
            formTester = newFormTester(path)
        }
        return formTester;
    }

    /**
     * If component cannot be found the component tree is logged out. helps to
     * determine the right path for a component.
     */
    override def getComponentFromLastRenderedPage(path: String, wantVisibleInHierarchy: Boolean): Component = {
        try {
            return super.getComponentFromLastRenderedPage(path, wantVisibleInHierarchy)
        }
        catch {
            case e: AssertionFailedError => {
                return findComponent(path, wantVisibleInHierarchy);
            }
        };
    }

    /**
     * Iterates through all components and looks for one with given name
     */
    def findComponent(name: String, wantVisibleInHierarchy: Boolean): Component = {
        val list = new ArrayList[ComponentData]();
        for (c <- WicketTesterHelper.getComponentData(getLastRenderedPage())) {
            if (c.path.endsWith(name)) {
                list.add(c);
            }
        }
        if (list.isEmpty()) {
            debugComponentTrees();
            fail("name: '" + name + "' does not exist for page: " + Classes.simpleName(getLastRenderedPage.getClass))
            return null;
        }
        if (list.size() > 1) {
            debugComponentTrees();
            fail("name: '" + name + "' is ambiguous for page: " + Classes.simpleName(getLastRenderedPage.getClass))
        }
        return super.getComponentFromLastRenderedPage(list.get(0).path, wantVisibleInHierarchy);
    }
}
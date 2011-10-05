package de.flower.test

import org.apache.wicket.util.tester.{WicketTesterHelper, WicketTester}
import org.apache.wicket.Component
import org.apache.wicket.protocol.http.WebApplication
import scala.collection.JavaConversions._
import org.apache.wicket.Component
import org.apache.wicket.util.tester.{FormTester, WicketTester, WicketTesterHelper}
import de.flower.common.ui.{LoggingSerializer, Css}
import org.apache.wicket.serialize.java.JavaSerializer
import scala.Boolean

/**
 * 
 * @author flowerrrr
 */

class RMTWicketTester(application: WebApplication) extends WicketTester(application) {



    /**
     * Get the text document that was written as part of this response.
     *
     * @return html markup
     */
    override def processRequest(): Boolean = {
        val b = super.processRequest();
        val page = getLastRenderedPage()
        if (page != null) {
            val serializer = new LoggingSerializer(new JavaSerializer("foobar"))
            serializer.serialize(page)
        }
        return b;
    }

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
        val cssClass = if (assertion)  Css.VALID else Css.ERROR
        assertContains("class=\"" + cssClass)
    }

    private var formTester: FormTester = null

    def getFormTester(path: String = "form"): FormTester = {
        if (formTester == null) {
            formTester = newFormTester(path)
        }
        return formTester;
    }

}
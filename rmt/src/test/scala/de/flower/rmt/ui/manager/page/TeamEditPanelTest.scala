package de.flower.rmt.ui.manager.page

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import teams.TeamEditPanel
import org.apache.wicket.Component
import org.apache.wicket.behavior.AttributeAppender
import org.testng.Assert._
import de.flower.common.util.ReflectionUtil
import org.apache.wicket.model.IModel
import javax.swing.text.html.CSS
import de.flower.common.ui.Css

/**
 * 
 * @author oblume
 */

class TeamEditPanelTest extends WicketTests {

    @Test
    def validateUniquenessConstraint() {
        val panel: TeamEditPanel = wicketTester.startPanel(classOf[TeamEditPanel]).asInstanceOf[TeamEditPanel]
        panel.init(null)
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        var border: Component = wicketTester.getComponentFromLastRenderedPage("form:name")
        val attributeAppender = getBehavior(border, classOf[AttributeAppender])
        var model: IModel[String] = ReflectionUtil.getField(attributeAppender, "replaceModel").asInstanceOf[IModel[String]]
        assertEquals(model.getObject(), "")
        // input name and validate field
        val formTester = wicketTester.newFormTester("form")
        val field = wicketTester.getComponentFromLastRenderedPage("form:name:name")
        formTester.setValue(field, "teamname")
        wicketTester.executeAjaxEvent(field, "onblur")
        wicketTester.dumpPage()
        // check if class="valid" is set in text field border
        assertEquals(model.getObject(), Css.VALID)
        // set teamname to existing team and revalidate field
        formTester.setValue(field, "Juve Amateure")
        wicketTester.executeAjaxEvent(field, "onblur")
        wicketTester.dumpPage()
        // check if class="valid" is set in text field border
        assertEquals(model.getObject(), Css.ERROR)


    }


}
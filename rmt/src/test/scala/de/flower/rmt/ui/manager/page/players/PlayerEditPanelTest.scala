package de.flower.rmt.ui.manager.page.players

import de.flower.rmt.test.WicketTests
import org.testng.annotations.Test
import org.testng.Assert._
import de.flower.rmt.model.User

/**
 * 
 * @author flowerrrr
 */

class PlayerEditPanelTest extends WicketTests {


    @Test
    def validateConstraints() {
        val panel: PlayerEditPanel = wicketTester.startPanel(classOf[PlayerEditPanel]).asInstanceOf[PlayerEditPanel]
        panel.init(null)
        // get user under test
        val form = wicketTester.getComponentFromLastRenderedPage("form")
        val userUnderTest = form.getDefaultModelObject().asInstanceOf[User]
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input email and validate field
        var email = wicketTester.getComponentFromLastRenderedPage("form:email:email")
        wicketTester.assertValidation(email, "", false) // field cannot be empty
        wicketTester.assertValidation(email, "foo@bar.com", true)
        wicketTester.assertValidation(email, "not-an-email-address", false) // invalid email format
        // set email to existing user and revalidate field  -> not unique validator must fire
        var user = userRepo.findOne(1)
        assertEquals(user.getClub(), userUnderTest.getClub)
        wicketTester.assertValidation(email, user.getEmail(), false)
        wicketTester.assertValidation(email, "foo@bar.com", true)
        // test fullname field
        var fullname = wicketTester.getComponentFromLastRenderedPage("form:fullname:fullname")
        wicketTester.assertValidation(fullname, "", false) // cannot be blank
        wicketTester.assertValidation(fullname, "foo bar", true)
        wicketTester.assertValidation(fullname, user.getFullname(), false) // must be unique per club
        // but fullname must not be unique across different clubs
        user = userRepo.findOne(5)
        assertNotEquals(user.getClub(), userUnderTest.getClub)
        wicketTester.assertValidation(fullname, user.getFullname(), true)

     }



}
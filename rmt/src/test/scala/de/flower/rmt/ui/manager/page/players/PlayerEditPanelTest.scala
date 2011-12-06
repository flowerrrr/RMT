package de.flower.rmt.ui.manager.page.players

import de.flower.rmt.test.AbstractWicketTests
import org.testng.annotations.Test
import org.testng.Assert._
import de.flower.rmt.model.User
import de.flower.rmt.ui.model.UserModel

/**
 * 
 * @author flowerrrr
 */

class PlayerEditPanelTest extends AbstractWicketTests {

    @Test
    def testRender() {
        wicketTester.startComponentInPage(new PlayerEditPanel(new UserModel()))
        wicketTester.dumpComponentWithPage()
    }

    @Test
    def validateConstraints() {
        wicketTester.startComponentInPage(new PlayerEditPanel(new UserModel()))
        // get user under test
        val form = wicketTester.getComponentFromLastRenderedPage("form")
        val userUnderTest = form.getDefaultModelObject().asInstanceOf[User]
        wicketTester.dumpPage()
        wicketTester.debugComponentTrees()
        // input email and validate field
        var email = wicketTester.getComponentFromLastRenderedPage("form:email:input")
        wicketTester.assertAjaxValidationError(email, "") // field cannot be empty
        wicketTester.assertNoAjaxValidationError(email, "foo@bar.com")
        wicketTester.assertAjaxValidationError(email, "not-an-email-address") // invalid email format
        // set email to existing user and re-validate field  -> not unique validator must fire
        var user = userRepo.findOne(1)
        assertEquals(user.getClub(), userUnderTest.getClub)
        wicketTester.assertAjaxValidationError(email, user.getEmail())
        wicketTester.assertNoAjaxValidationError(email, "foo@bar.com")
        // test fullname field
        var fullname = wicketTester.getComponentFromLastRenderedPage("form:fullname:input")
        wicketTester.assertAjaxValidationError(fullname, "") // cannot be blank
        wicketTester.assertNoAjaxValidationError(fullname, "foo bar")
        wicketTester.assertAjaxValidationError(fullname, user.getFullname()) // must be unique per club
        // but fullname must not be unique across different clubs
        user = userRepo.findOne(5)
        assertNotEquals(user.getClub(), userUnderTest.getClub)
        wicketTester.assertNoAjaxValidationError(fullname, user.getFullname())

     }



}
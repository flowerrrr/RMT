package de.flower.rmt.ui.manager.page.player;

import de.flower.rmt.model.User;
import de.flower.rmt.model.User_;
import de.flower.rmt.test.AbstractWicketIntegrationTests;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */

public class PlayerGeneralPanelITest extends AbstractWicketIntegrationTests {

    @Test
    public void validateConstraints() {
        wicketTester.startComponentInPage(new PlayerGeneralPanel("panel", new UserModel()));
        // get user under test
        Form<User> form = (Form<User>) wicketTester.getComponentFromLastRenderedPage("form");
        User userUnderTest = form.getModelObject();
        wicketTester.dumpPage();
        wicketTester.debugComponentTrees();
        // input email and validate field
        Component email = wicketTester.getComponentFromLastRenderedPage("email:input");
        wicketTester.assertAjaxValidationError(email, ""); // field cannot be empty
        wicketTester.assertNoAjaxValidationError(email, "foo@bar.com");
        wicketTester.assertAjaxValidationError(email, "not-an-email-address"); // invalid email format
        // set email to existing user and re-validate field  -> not unique validator must fire
        User user = userManager.loadById(1L, User_.club);
        assertEquals(user.getClub(), userUnderTest.getClub());
        wicketTester.assertAjaxValidationError(email, user.getEmail());
        wicketTester.assertNoAjaxValidationError(email, "foo@bar.com");
        // test fullname field
        Component fullname = wicketTester.getComponentFromLastRenderedPage("fullname:input");
        wicketTester.assertAjaxValidationError(fullname, "");// cannot be blank
        wicketTester.assertNoAjaxValidationError(fullname, "foo bar");
        wicketTester.assertAjaxValidationError(fullname, user.getFullname()); // must be unique per club
        // but fullname must not be unique across different clubs
        user = userRepo.findOne(5L);
        // assertNotEquals(user.getClub().getId(), userUnderTest.getClub().getId());
        wicketTester.assertNoAjaxValidationError(fullname, user.getFullname());
    }
}
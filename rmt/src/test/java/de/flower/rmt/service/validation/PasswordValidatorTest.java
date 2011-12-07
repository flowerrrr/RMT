package de.flower.rmt.service.validation;

import de.flower.rmt.model.User;
import de.flower.rmt.service.type.Password;
import de.flower.rmt.test.AbstractIntegrationTests;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * @author flowerrrr
 */
public class PasswordValidatorTest extends AbstractIntegrationTests {

    @Test
    public void testValidation() {
        User user = testData.createUsers(1).get(0);
        Password password = new Password(user.getId());
        String newPassword = "foobar";
        password.setNewPassword(newPassword);
        password.setNewPasswordRepeat(newPassword);
        password.setOldPassword(passwordGenerator.generatePassword());
        Set<ConstraintViolation<Password>> violations = validator.validate(password);
        assertTrue(violations.isEmpty(), violations.toString());

        // now validate with invalid password
        password.setOldPassword("klaöjsaf");
        violations = validator.validate(password);
        assertTrue(violations.size() == 1);
    }

}

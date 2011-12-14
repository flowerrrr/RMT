package de.flower.common.service.security;

import de.flower.rmt.test.AbstractIntegrationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.testng.annotations.Test;

/**
 * Class used to encode passwords for initial filling of database.
 * Saves us from using a command line tool.
 *
 * @author flowerrrr
 */
public class PasswordEncoderTool extends AbstractIntegrationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void encodePassword() {
        String password = "1234";
        String encryptedPassword = passwordEncoder.encodePassword(password, null);
        log.info("[" + password + "] -> [" + encryptedPassword + "].");
    }

}

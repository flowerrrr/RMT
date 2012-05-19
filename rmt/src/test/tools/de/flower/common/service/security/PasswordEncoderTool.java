package de.flower.common.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.testng.annotations.Test;

/**
 * Class used to encode passwords for initial filling of database.
 * Saves us from using a command line tool.
 *
 * @author flowerrrr
 */
public class PasswordEncoderTool {

    private final static Logger log = LoggerFactory.getLogger(PasswordEncoderTool.class);

    private PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    @Test
    public void encodePassword() {
        String password = "1234";
        String encryptedPassword = passwordEncoder.encodePassword(password, null);
        log.info("[" + password + "] -> [" + encryptedPassword + "].");
    }

}

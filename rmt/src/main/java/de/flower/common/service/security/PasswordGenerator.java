package de.flower.common.service.security;

import org.springframework.stereotype.Service;

/**
 * @author flowerrrr
 */
@Service
public class PasswordGenerator implements IPasswordGenerator {

    @Override
    public String generatePassword() {
        return "1234";
    }
}

package de.flower.common.service.security;

import org.springframework.stereotype.Service;

/**
 * @author flowerrrr
 */
@Service
public class PasswordGenerator implements IPasswordGenerator {

    @Override
    public String generatePassword() {
        // TODO (flowerrrr - 07.12.11) replace with some fancy algorithm.
        return "1234";
    }
}

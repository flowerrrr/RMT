package de.flower.rmt.service.validation;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.dto.Password;
import de.flower.rmt.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PasswordValidator {

    @Autowired
    private UserManager userManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isValid(final Object bean) {
        Password password = (Password) bean;
        User user = userManager.loadById(password.getUserId());
        return passwordEncoder.isPasswordValid(user.getEncryptedPassword(), password.getOldPassword(), null);
    }
}

package de.flower.rmt.service.validation;

import de.flower.common.validation.spring.IBeanValidator;
import de.flower.rmt.model.User;
import de.flower.rmt.model.type.Password;
import de.flower.rmt.service.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author flowerrrr
 */
@Service
public class PasswordValidator implements IBeanValidator {

    @Autowired
    private IUserManager userManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(final Object bean) {
        Password password = (Password) bean;
        User user = userManager.loadById(password.getUserId());
        return passwordEncoder.isPasswordValid(user.getEncryptedPassword(), password.getOldPassword(), null);
    }
}

package de.flower.rmt.service.mail;

import de.flower.rmt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author flowerrrr
 */
@Service
public class NotificationService implements INotificationService {

    @Autowired
    private IMailService mailService;

    @Autowired
    private ITemplateService templateService;

    @Override
    public void sendResetPasswordMail(final User user) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        String subject = templateService.mergeTemplate(EmailTemplate.PASSWORD_RESET.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.PASSWORD_RESET.getContent(), model);
        mailService.sendMail(user.getEmail(), subject, content);
    }

    @Override
    public void sendInvitationNewUser(final User user, final User manager) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("manager", manager);
        model.put("club", user.getClub());
        String subject = templateService.mergeTemplate(EmailTemplate.INVITATION_NEWUSER.getSubject(), model);
        String content = templateService.mergeTemplate(EmailTemplate.INVITATION_NEWUSER.getContent(), model);
        mailService.sendMail(user.getEmail(), subject, content);
    }

}

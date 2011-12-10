package de.flower.rmt.service.mail;

/**
 * @author flowerrrr
 */
public enum EmailTemplate {

    PASSWORD_RESET("PasswordReset.content.vm", "PasswordReset.subject.vm");

    private String content;

    private String subject;

    EmailTemplate(final String content, String subject) {
        this.content = content;
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

   public String getSubject() {
        return subject;
    }
}

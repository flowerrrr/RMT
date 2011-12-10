package de.flower.rmt.service.mail;

/**
 * @author flowerrrr
 */
public enum EmailTemplate {

    PASSWORD_RESET,
    INVITATION_NEWUSER;

    public String getContent() {
        return (this.name() + ".content.vm").toLowerCase()  ;
    }

   public String getSubject() {
       return (this.name() + ".subject.vm").toLowerCase()  ;
    }
}

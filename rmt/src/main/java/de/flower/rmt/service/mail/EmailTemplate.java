package de.flower.rmt.service.mail;


public enum EmailTemplate {

    PASSWORD_RESET,
    INVITATION_NEWUSER,
    INVITATION_STATUSCHANGED,
    NOTIFICATION_EVENT,
    NORESPONSE_REMINDER,
    UNSURE_REMINDER,
    EVENT_CANCELED,
    EVENT_DETAILS,
    EVENT_ICALENDAR;

    public String getTemplate() {
        return (this.name() + ".vm").toLowerCase();
    }

    public String getContent() {
        return (this.name() + ".content.vm").toLowerCase();
    }

    public String getSubject() {
        return (this.name() + ".subject.vm").toLowerCase();
    }
}

package de.flower.rmt.model.type;

import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class Notification implements Serializable {

    private List<InternetAddress> recipients = new ArrayList<InternetAddress>();

    private String subject;

    private String body;

    private Boolean bccMySelf;

    public List<InternetAddress> getRecipients() {
        return recipients;
    }

    public void setRecipients(final List<InternetAddress> recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public Boolean isBccMySelf() {
        return bccMySelf;
    }

    public void setBccMySelf(final Boolean bccMySelf) {
        this.bccMySelf = bccMySelf;
    }

    public void addRecipient(final String address, final String personal) {
        InternetAddress iAddress;
        try {
            iAddress = new InternetAddress(address, personal);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        recipients.add(iAddress);
    }

    /**
     * Returns email addresses (only address part) as string list.
     */
    public List<String> getAddressList() {
        List<String> list = new ArrayList<String>();
        for (InternetAddress iAddress : recipients) {
            list.add(iAddress.getAddress());
        }
        return list;
    }
}

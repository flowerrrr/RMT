package de.flower.rmt.model.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;

import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
public class Notification implements Serializable {

    @NotEmpty
    private List<InternetAddress> recipients = new ArrayList<InternetAddress>();

    @NotBlank
    private String subject;

    @NotBlank
    private String body;

    private boolean bccMySelf;

    private Attachment attachment;

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

    public boolean isBccMySelf() {
        return bccMySelf;
    }

    public void setBccMySelf(final boolean bccMySelf) {
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

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(final Attachment attachment) {
        this.attachment = attachment;
    }

    public static class Attachment implements Serializable {

        public String name;

        public String contentType;

        public byte[] data;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(final String contentType) {
            this.contentType = contentType;
        }


        public InputStreamSource getInputStreamSource() {
            return new ByteArrayResource(data);
        }

        @Override
        public String toString() {
            return "Attachment{" +
                    "name='" + name + '\'' +
                    ", contentType='" + contentType + '\'' +
                    ", data=" + data == null ? "" : new String(data) +
                    '}';
        }
    }
}

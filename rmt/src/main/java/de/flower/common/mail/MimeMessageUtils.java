package de.flower.common.mail;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

/**
 * @author flowerrrr
 */
public class MimeMessageUtils {

    private MimeMessageUtils() {
    }

    public static String toString(MimeMessage mimeMessage) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            mimeMessage.writeTo(os);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "<mime message>\n" + os.toString() + "\n</mime message>";
    }
}

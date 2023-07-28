package de.flower.rmt.model.db.type.activity;

import org.testng.annotations.Test;


public class ActivityMessageTest {

    /**
     * Checks against package or class renaming which would break existing messages stored
     * as serialized objects in database.
     */
    @Test
    public void testDeserialize() throws Exception {
        String packageName = "de.flower.rmt.mo" + "" /* make string safe against refactoring */ + "del.db.type.activity.";
        Class.forName(packageName + "BlogUpdate" + "Message");
        Class.forName(packageName + "EmailSent" + "Message");
        Class.forName(packageName + "EventUpdate" + "Message");
        Class.forName(packageName + "InvitationUpdate" + "Message");
        Class.forName(packageName + "InvitationUpdate" + "Message2");
        Class.forName("de.flower.rmt.mo" + "" /* refactoring safe */ + "del.db.type.EventType");
    }

}

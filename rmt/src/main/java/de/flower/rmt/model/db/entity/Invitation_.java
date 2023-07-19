package de.flower.rmt.model.db.entity;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;
import java.util.Date;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Invitation.class)
public abstract class Invitation_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<Invitation, Date> date;
	public static volatile SingularAttribute<Invitation, Date> invitationSentDate;
	public static volatile ListAttribute<Invitation, Comment> comments;
	public static volatile SingularAttribute<Invitation, Boolean> unsureReminderSent;
	public static volatile SingularAttribute<Invitation, Boolean> noResponseReminderSent;
	public static volatile SingularAttribute<Invitation, Event> event;
	public static volatile SingularAttribute<Invitation, User> user;
	public static volatile SingularAttribute<Invitation, Boolean> invitationSent;
	public static volatile SingularAttribute<Invitation, Date> unsureReminderSentDate;
	public static volatile SingularAttribute<Invitation, RSVPStatus> status;
	public static volatile SingularAttribute<Invitation, String> guestName;
	public static volatile SingularAttribute<Invitation, Date> noResponseReminderSentDate;

}


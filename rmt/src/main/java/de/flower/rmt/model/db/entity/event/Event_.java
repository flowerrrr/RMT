package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Opponent;
import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.entity.Venue;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@StaticMetamodel(Event.class)
public abstract class Event_ extends de.flower.rmt.model.db.entity.AbstractClubRelatedEntity_ {

	public static volatile SingularAttribute<Event, DateTime> dateTime;
	public static volatile SingularAttribute<Event, String> summary;
	public static volatile SingularAttribute<Event, Venue> venue;
	public static volatile SingularAttribute<Event, Boolean> canceled;
	public static volatile SingularAttribute<Event, User> createdBy;
	public static volatile ListAttribute<Event, Invitation> invitations;
	public static volatile SingularAttribute<Event, Opponent> opponent;
	public static volatile SingularAttribute<Event, DateTime> dateTimeEnd;
	public static volatile SingularAttribute<Event, String> comment;
	public static volatile SingularAttribute<Event, Team> team;
	public static volatile SingularAttribute<Event, Boolean> invitationSent;

}


package de.flower.rmt.model.db.entity;

import de.flower.rmt.model.db.entity.CalItem.Type;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@StaticMetamodel(CalItem.class)
public abstract class CalItem_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<CalItem, String> summary;
	public static volatile SingularAttribute<CalItem, Boolean> allDay;
	public static volatile SingularAttribute<CalItem, DateTime> startDateTime;
	public static volatile SingularAttribute<CalItem, Boolean> autoDecline;
	public static volatile SingularAttribute<CalItem, DateTime> endDateTime;
	public static volatile SingularAttribute<CalItem, Type> type;
	public static volatile SingularAttribute<CalItem, User> user;

}


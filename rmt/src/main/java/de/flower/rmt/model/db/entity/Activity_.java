package de.flower.rmt.model.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Activity.class)
public abstract class Activity_ extends AbstractClubRelatedEntity_ {

	public static volatile SingularAttribute<Activity, Date> date;
	public static volatile SingularAttribute<Activity, Serializable> message;
	public static volatile SingularAttribute<Activity, User> user;

}


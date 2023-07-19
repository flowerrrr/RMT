package de.flower.rmt.model.db.entity;

import de.flower.rmt.model.db.entity.event.Event;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Lineup.class)
public abstract class Lineup_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<Lineup, String> name;
	public static volatile SingularAttribute<Lineup, Boolean> published;
	public static volatile SingularAttribute<Lineup, Event> event;
	public static volatile ListAttribute<Lineup, LineupItem> items;

}


package de.flower.rmt.model.db.entity;

import de.flower.rmt.model.db.entity.event.Event;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EventTeam.class)
public abstract class EventTeam_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile ListAttribute<EventTeam, EventTeamPlayer> players;
	public static volatile SingularAttribute<EventTeam, String> name;
	public static volatile SingularAttribute<EventTeam, Integer> rank;
	public static volatile SingularAttribute<EventTeam, Event> event;

}


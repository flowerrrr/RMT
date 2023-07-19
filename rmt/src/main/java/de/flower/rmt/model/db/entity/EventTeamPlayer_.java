package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EventTeamPlayer.class)
public abstract class EventTeamPlayer_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<EventTeamPlayer, Invitation> invitation;
	public static volatile SingularAttribute<EventTeamPlayer, EventTeam> eventTeam;
	public static volatile SingularAttribute<EventTeamPlayer, Integer> order;

}


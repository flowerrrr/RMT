package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Team.class)
public abstract class Team_ extends AbstractClubRelatedEntity_ {

	public static volatile ListAttribute<Team, Player> players;
	public static volatile SingularAttribute<Team, String> name;
	public static volatile SingularAttribute<Team, String> url;

}


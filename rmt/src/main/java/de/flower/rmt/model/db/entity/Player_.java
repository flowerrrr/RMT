package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Player.class)
public abstract class Player_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<Player, Boolean> notification;
	public static volatile SingularAttribute<Player, Boolean> optional;
	public static volatile SingularAttribute<Player, Boolean> retired;
	public static volatile SingularAttribute<Player, Team> team;
	public static volatile SingularAttribute<Player, User> user;

}


package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(LineupItem.class)
public abstract class LineupItem_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<LineupItem, Long> absTop;
	public static volatile SingularAttribute<LineupItem, Double> relLeft;
	public static volatile SingularAttribute<LineupItem, Double> relTop;
	public static volatile SingularAttribute<LineupItem, Invitation> invitation;
	public static volatile SingularAttribute<LineupItem, Long> absLeft;
	public static volatile SingularAttribute<LineupItem, Lineup> lineup;

}


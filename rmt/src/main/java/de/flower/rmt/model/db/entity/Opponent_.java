package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Opponent.class)
public abstract class Opponent_ extends AbstractClubRelatedEntity_ {

	public static volatile SingularAttribute<Opponent, String> name;
	public static volatile SingularAttribute<Opponent, String> url;

}


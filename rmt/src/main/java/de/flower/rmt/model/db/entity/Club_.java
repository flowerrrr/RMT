package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Club.class)
public abstract class Club_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<Club, Double> lng;
	public static volatile SingularAttribute<Club, String> name;
	public static volatile SingularAttribute<Club, Double> lat;

}


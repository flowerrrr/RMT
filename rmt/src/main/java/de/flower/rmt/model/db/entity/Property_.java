package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Property.class)
public abstract class Property_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<Property, Club> club;
	public static volatile SingularAttribute<Property, String> name;
	public static volatile SingularAttribute<Property, User> user;
	public static volatile SingularAttribute<Property, String> value;

}


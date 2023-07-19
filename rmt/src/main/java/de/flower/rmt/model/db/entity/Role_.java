package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
public abstract class Role_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<Role, String> authority;
	public static volatile SingularAttribute<Role, User> user;

}


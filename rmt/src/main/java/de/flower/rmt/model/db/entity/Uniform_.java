package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Uniform.class)
public abstract class Uniform_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<Uniform, String> shirt;
	public static volatile SingularAttribute<Uniform, String> socks;
	public static volatile SingularAttribute<Uniform, String> name;
	public static volatile SingularAttribute<Uniform, Team> team;
	public static volatile SingularAttribute<Uniform, String> shorts;

}


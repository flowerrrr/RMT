package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Venue.class)
public abstract class Venue_ extends AbstractClubRelatedEntity_ {

	public static volatile SingularAttribute<Venue, String> address;
	public static volatile SingularAttribute<Venue, Double> lng;
	public static volatile SingularAttribute<Venue, String> name;
	public static volatile SingularAttribute<Venue, Double> lat;

}


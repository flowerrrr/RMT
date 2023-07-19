package de.flower.rmt.model.db.entity;

import de.flower.rmt.model.db.entity.event.Event;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BArticle.class)
public abstract class BArticle_ extends AbstractClubRelatedEntity_ {

	public static volatile SingularAttribute<BArticle, String> heading;
	public static volatile SingularAttribute<BArticle, User> author;
	public static volatile SingularAttribute<BArticle, String> text;
	public static volatile SingularAttribute<BArticle, Event> event;

}


package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BComment.class)
public abstract class BComment_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<BComment, User> author;
	public static volatile SingularAttribute<BComment, String> text;
	public static volatile SingularAttribute<BComment, BArticle> article;

}


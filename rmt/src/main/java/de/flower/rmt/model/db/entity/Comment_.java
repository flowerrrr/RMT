package de.flower.rmt.model.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Comment.class)
public abstract class Comment_ extends de.flower.common.model.db.entity.AbstractBaseEntity_ {

	public static volatile SingularAttribute<Comment, Invitation> invitation;
	public static volatile SingularAttribute<Comment, User> author;
	public static volatile SingularAttribute<Comment, String> text;

}


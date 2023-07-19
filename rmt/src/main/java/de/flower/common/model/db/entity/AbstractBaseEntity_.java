package de.flower.common.model.db.entity;

import de.flower.common.model.db.type.ObjectStatus;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AbstractBaseEntity.class)
public abstract class AbstractBaseEntity_ {

	public static volatile SingularAttribute<AbstractBaseEntity, Date> updateDate;
	public static volatile SingularAttribute<AbstractBaseEntity, ObjectStatus> objectStatus;
	public static volatile SingularAttribute<AbstractBaseEntity, Long> id;
	public static volatile SingularAttribute<AbstractBaseEntity, Date> createDate;

}


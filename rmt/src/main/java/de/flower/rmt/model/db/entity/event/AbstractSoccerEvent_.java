package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.model.db.type.Surface;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.LocalTime;

@StaticMetamodel(AbstractSoccerEvent.class)
public abstract class AbstractSoccerEvent_ extends de.flower.rmt.model.db.entity.event.Event_ {

	public static volatile SingularAttribute<AbstractSoccerEvent, Uniform> uniform;
	public static volatile SingularAttribute<AbstractSoccerEvent, LocalTime> kickoff;
	public static volatile ListAttribute<AbstractSoccerEvent, Surface> surfaceList;

}


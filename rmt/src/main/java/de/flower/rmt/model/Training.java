package de.flower.rmt.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author oblume
 */
@Entity
@DiscriminatorValue("Training")
public class Training extends Event {

}
